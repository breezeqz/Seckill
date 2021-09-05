package com.site.seckill.controller;

import com.site.seckill.bo.GoodsBo;
import com.site.seckill.common.Const;
import com.site.seckill.entity.User;
import com.site.seckill.redis.GoodsKey;
import com.site.seckill.redis.RedisService;
import com.site.seckill.redis.UserKey;
import com.site.seckill.result.CodeMsg;
import com.site.seckill.result.Result;
import com.site.seckill.service.SeckillGoodsService;
import com.site.seckill.util.CookieUtil;
import com.site.seckill.vo.GoodsDetailVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    RedisService redisService;

    @Autowired
    SeckillGoodsService seckillGoodsService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ApplicationContext applicationContext;

    @RequestMapping("/list")
    @ResponseBody
    public String list(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //修改前
//         List<GoodsBo> goodsList = seckillGoodsService.getSeckillGoodsList();
//         model.addAttribute("goodsList", goodsList);
//    	 return "goods_list";
        //修改后(使用redis缓存页面)
        //首页吞吐量3000/s
        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if(html!=null){
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File("D:\\a.html")));
            bw.write(html.toCharArray(),0,html.length());
            bw.close();
        }

        if(!StringUtils.isEmpty(html)) {
            return html;
        }
        List<GoodsBo> goodsList = seckillGoodsService.getSeckillGoodsList();
        model.addAttribute("goodsList", goodsList);
        SpringWebContext ctx = new SpringWebContext(request,response,
                request.getServletContext(),request.getLocale(), model.asMap(), applicationContext );
        //手动渲染
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);//goods_list为模板名
        if(!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsList, "", html , Const.RedisCacheExtime.GOODS_LIST);
        }

        return html;
    }
    @RequestMapping("/to_detail2/{goodsId}")
    @ResponseBody
    public String detail2(Model model,
                         @PathVariable("goodsId")long goodsId ,HttpServletRequest request  ,HttpServletResponse response  ) {
        String loginToken = CookieUtil.readLoginToken(request);
        User user = redisService.get(UserKey.getByName, loginToken, User.class);
        model.addAttribute("user", user);

        //取缓存
        String html = redisService.get(GoodsKey.getGoodsDetail, ""+goodsId, String.class);
        if(!StringUtils.isEmpty(html)) {
            return html;
        }
        //缓存没有去数据库查询
        GoodsBo goods = seckillGoodsService.getseckillGoodsBoByGoodsId(goodsId);
        if(goods == null){
            return "没有找到该页面";
        }else {
            model.addAttribute("goods", goods);
            long startAt = goods.getStartDate().getTime();
            long endAt = goods.getEndDate().getTime();
            long now = System.currentTimeMillis();

            int miaoshaStatus = 0;
            int remainSeconds = 0;
            if(now < startAt ) {//秒杀还没开始，倒计时
                miaoshaStatus = 0;
                remainSeconds = (int)((startAt - now )/1000);
            }else  if(now > endAt){//秒杀已经结束
                miaoshaStatus = 2;
                remainSeconds = -1;
            }else {//秒杀进行中
                miaoshaStatus = 1;
                remainSeconds = 0;
            }
            model.addAttribute("seckillStatus", miaoshaStatus);
            model.addAttribute("remainSeconds", remainSeconds);
            SpringWebContext ctx = new SpringWebContext(request,response,
                    request.getServletContext(),request.getLocale(), model.asMap(), applicationContext );
            html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);
            if(!StringUtils.isEmpty(html)) {
                //（缓存html页面）设置键值对的超时时间，其中值为html页面
                redisService.set(GoodsKey.getGoodsDetail, ""+goodsId, html , Const.RedisCacheExtime.GOODS_INFO);
            }
            return html;
        }
    }
    @RequestMapping("/detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> detail(Model model,
                                        @PathVariable("goodsId")long goodsId , HttpServletRequest request  ) {
        String loginToken = CookieUtil.readLoginToken(request);//获取当前请求的cookie中seckill_login_token对应的值（sessionid）
        //使用sessionid去redis中查询用户信息
        User user = redisService.get(UserKey.getByName, loginToken, User.class);

        GoodsBo goods = seckillGoodsService.getseckillGoodsBoByGoodsId(goodsId);
        if(goods == null){
            return Result.error(CodeMsg.NO_GOODS);
        }else {
            model.addAttribute("goods", goods);
            long startAt = goods.getStartDate().getTime();
            long endAt = goods.getEndDate().getTime();
            long now = System.currentTimeMillis();

            int miaoshaStatus = 0;//秒杀状态，0:秒杀未开始,1:秒杀进行中,2:秒杀已结束
            int remainSeconds = 0;//距离秒杀开始剩余秒数
            if(now < startAt ) {//秒杀还没开始，倒计时
                miaoshaStatus = 0;
                remainSeconds = (int)((startAt - now )/1000);
            }else  if(now > endAt){//秒杀已经结束
                miaoshaStatus = 2;
                remainSeconds = -1;
            }else {//秒杀进行中
                miaoshaStatus = 1;
                remainSeconds = 0;
            }
            GoodsDetailVo vo = new GoodsDetailVo();
            vo.setGoods(goods);
            vo.setUser(user);
            vo.setRemainSeconds(remainSeconds);
            vo.setMiaoshaStatus(miaoshaStatus);
            return Result.success(vo);
        }
    }
}

