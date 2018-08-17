package com.pym.uiproject.net;

import com.pym.uiproject.app.main.model.NewHomeInfo;
import com.pym.uiproject.app.playvideo.model.VideoLiveList;
import com.pym.uiproject.app.playvideo.model.VideoLiveTable;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * ClientAPI
 *
 * @author Peng YanMing 2016/12/9
 */
public interface ClientAPI {
    /**
     * 获取直播类别
     *
     * @return VideoLiveTable
     */
    @GET("videolive/home/channels?iid=39921234076&device_id=41657683759&ac=wifi&channel=vivo&aid=32&app_name=video_article&version_code=664&version_name=6.6.4&device_platform=android&ab_version=344692%2C418416%2C321290%2C425685%2C424183%2C426435%2C381398%2C439901%2C432388%2C436132%2C374100%2C419921%2C398044%2C434356%2C439844%2C377320%2C423674%2C409606%2C378355%2C434430%2C437663%2C430495%2C435541%2C437658&ssmix=a&device_type=vivo+X20A&device_brand=vivo&language=zh&os_api=25&os_version=7.1.1&uuid=867649036961114&openudid=d38a3a1ca6fafba1&manifest_version_code=264&resolution=1080*2160&dpi=480&update_version_code=66404&_rticket=1533518545731&fp=jrTqF2GtFSc7FlcSJlU1FYmIJYK7&rom_version=Funtouch+OS_3.2_PD1709_A_1.14.3")
    Observable<VideoLiveTable> getVideoliveTable();

    /**
     * 获取直播列表
     * feeds_type=0下拉feeds_type=1上拉
     */
    @GET("videolive/home/page/v2?page_type=0&cursor=0&app_life_id=1533518545772&iid=39921234076&device_id=41657683759&ac=wifi&channel=vivo&aid=32&app_name=video_article&version_code=664&version_name=6.6.4&device_platform=android&ab_version=344692%2C418416%2C321290%2C425685%2C424183%2C426435%2C381398%2C439901%2C432388%2C436132%2C374100%2C419921%2C398044%2C434356%2C439844%2C377320%2C423674%2C409606%2C378355%2C434430%2C437663%2C430495%2C435541%2C437658&ssmix=a&device_type=vivo+X20A&device_brand=vivo&language=zh&os_api=25&os_version=7.1.1&uuid=867649036961114&openudid=d38a3a1ca6fafba1&manifest_version_code=264&resolution=1080*2160&dpi=480&update_version_code=66404&_rticket=1533518545846&fp=jrTqF2GtFSc7FlcSJlU1FYmIJYK7&rom_version=Funtouch+OS_3.2_PD1709_A_1.14.3")
    Observable<VideoLiveList> getVideoliveList(@Query("id") int id, @Query("feeds_type") int feeds_type, @Query("page") int page);
    // device_id=41657683759
    // &ac=wifi
    // &channel=vivo
    // &aid=32
    // &app_name=video_article
    // &version_code=664
    // &version_name=6.6.4
    // &device_platform=android
    // &ssmix=a
    // &device_type=vivo+X20A
    // &device_brand=vivo
    // &language=zh
    // &os_api=25
    // &os_version=7.1.1
    // &uuid=867649036961114
    // &openudid=d38a3a1ca6fafba1
    // &manifest_version_code=264
    // &resolution=1080*2160
    // &dpi=480
    // &update_version_code=66404
    // &_rticket=1533708507711
    // &rom_version=Funtouch+OS_3.2_PD1709_A_1.14.3
    /**
     * 获取新闻列表
     */
    @FormUrlEncoded
    @POST("video/app/stream/v51/?")
    Observable<NewHomeInfo> getNewListInfo(@Field("device_id") String device_id,
                                             @Field("ac") String ac,
                                             @Field("channel") String channel,
                                             @Field("aid") String aid,
                                             @Field("app_name") String app_name,
                                             @Field("version_code") String version_code,
                                             @Field("version_name") String version_name,
                                             @Field("device_platform") String device_platform,
                                             @Field("ssmix") String ssmix,
                                             @Field("device_type") String device_type,
                                             @Field("device_brand") String device_brand,
                                             @Field("language") String language,
                                             @Field("os_api") String os_api,
                                             @Field("os_version") String os_version,
                                             @Field("uuid") String uuid,
                                             @Field("openudid") String openudid,
                                             @Field("manifest_version_code") String manifest_version_code,
                                             @Field("resolution") int resolution,
                                             @Field("dpi") String dpi,
                                             @Field("update_version_code") String update_version_code,
                                             @Field("_rticket") String _rticket,
                                             @Field("rom_version") String rom_version,
                                             @Field("page") int page);
//    http://display.miguvideo.com/display/v1/data/comps/9bedf04576884b3b845519cf1a371a44?page=3
//<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
//    /* **************************************************************************** *
//     * *********************************** 知乎日报 ******************************** *
//     * **************************************************************************** */
//
//
//    /**
//     * 获取今日日报新闻列表 ( 最长缓存一天 60 * 60 * 24 )
//     *
//     * @return Daily
//     */
//    // 指定返回复用时间为 60s
//    @Headers("Cache-Control: max-stale=60")
//    @GET("news/latest")
//    Observable<Daily> getDaily();
//
//    /**
//     * 获取新闻
//     *
//     * @param newsId long
//     * @return News
//     */
//    @Headers("Cache-Control: max-stale=3600")
//    @GET("news/{newsId}")
//    Observable<News> getNews(@Path("newsId") long newsId);
//
//    /**
//     * 获取往期日报
//     *
//     * @param date yyyyMMdd
//     * @return Daily
//     */
//    @Headers("Cache-Control: max-stale=86400")
//    @GET("news/before/{date}")
//    Observable<Daily> getDailyBefore(@Path("date") String date);
//
//
//    /* **************************************************************************** *
//     * *********************************** 干货集中营福利 *************************** *
//     * **************************************************************************** */
//
//
//    /**
//     * 获取福利
//     *
//     * @param limit 分页大小
//     * @param no    页码
//     * @return Result
//     */
//    @Headers("Cache-Control: max-stale=1800")
//    @GET("http://gank.io/api/data/福利/{limit}/{no}")
//    Observable<Result> getGankImage(@Path("limit") int limit, @Path("no") int no);
//
//
//    /* **************************************************************************** *
//     * *********************************** 豆瓣妹子 ******************************** *
//     * **************************************************************************** */
//
//
//    @Headers("Cache-Control: max-stale=60")
//    @GET("http://www.dbmeinv.com/dbgroup/rank.htm")
//    Observable<ResponseBody> getDoubanRank(@Query("pager_offset") int page);
//
//    @Headers("Cache-Control: max-stale=60")
//    @GET("http://www.dbmeinv.com/dbgroup/show.htm")
//    Observable<ResponseBody> getDoubanShow(@Query("cid") String type, @Query("pager_offset") int page);
//
//
//    /* **************************************************************************** *
//     * *********************************** mzitu ********************************** *
//     * **************************************************************************** */
//
//
//    @Headers("Cache-Control: max-stale=1800")
//    @GET("http://www.mzitu.com/zipai/{offset}/")
//    Observable<ResponseBody> getMzituZipai(@Path("offset") String offset);
//
//    @Headers("Cache-Control: max-stale=1800")
//    @GET("http://www.mzitu.com/{type}/page/{offset}/")
//    Observable<ResponseBody> getMzituOther(@Path("type") String type, @Path("offset") int offset);
//
//    @Headers("Cache-Control: max-stale=1800")
//    @GET("http://m.mzitu.com/{type}/page/{offset}/")
//    Observable<ResponseBody> getM(@Path("type") String type, @Path("offset") int offset);
//
//
//    /* **************************************************************************** *
//     * *********************************** meizitu ******************************** *
//     * **************************************************************************** */
//
//
//    @Headers("Cache-Control: max-stale=1800")
//    @GET("http://m.meizitu.com/index.html")
//    Observable<ResponseBody> getMeizituHome();
//
//    @Headers("Cache-Control: max-stale=1800")
//    @GET("http://m.meizitu.com/a/{type}_{offset}.html")
//    Observable<ResponseBody> getMeizitu(@Path("type") String type, @Path("offset") int offset);
//
//
//    /* **************************************************************************** *
//     * *********************************** 知乎看图 ******************************** *
//     * **************************************************************************** */
//
//
//    @Headers("Cache-Control: max-stale=86400")
//    @GET("https://raw.githubusercontent.com/gavinxxxxxx/Sensual/master/json/{type}")
//    Observable<List<Capture>> getZhihuList(@Path("type") String type);
//
//    @Headers({
//            "authorization: oauth c3cef7c66a1843f8b3a9e6a1e3160e20",
//            "Cache-Control: max-stale=3600"
//    })
//    @GET("https://www.zhihu.com/api/v4/questions/{id}/answers")
//    Observable<ResponseBody> getZhihuAnswer(
//            @Path("id") long id,
//            @Query("include") String include,
//            @Query("limit") int limit,
//            @Query("offset") int offset);
//
//    @Headers("Cache-Control: max-stale=3600")
//    @GET("https://www.zhihu.com/collection/{id}")
//    Observable<ResponseBody> getZhihuCollection(
//            @Path("id") long id,
//            @Query("page") int offset);
//
//
//    /* **************************************************************************** *
//     * *********************************** 煎蛋妹子 ******************************** *
//     * **************************************************************************** */
//
//
//    @Headers({
//            "User-Agent: a",
//            "Cache-Control: max-stale=60"
//    })
//    // "ooxx/page-75#comments"
//    @GET("http://jandan.net/ooxx/{offset}")
//    Observable<ResponseBody> getJiandan(@Path("offset") String page);
//
//    @Headers("Cache-Control: max-stale=86400")
//    @GET("https://raw.githubusercontent.com/gavinxxxxxx/Sensual/master/json/{type}")
//    Observable<ResponseBody> getJiandanTop(@Path("type") String tpye);
//
//
//    /* **************************************************************************** *
//     * *********************************** 买家秀 ********************************** *
//     * **************************************************************************** */
//
//
//    @Headers("Cache-Control: max-stale=60")
//    @GET("http://www.mjxzs.cc/")
//    Observable<ResponseBody> getMaijiaxiu();
//
//
//    /* **************************************************************************** *
//     * *********************************** Topit ********************************** *
//     * **************************************************************************** */
//
//    @Headers("Cache-Control: max-stale=3600")
//    @GET("https://raw.githubusercontent.com/gavinxxxxxx/Sensual/master/json/topitme_exclude_album.json")
//    Observable<List<Capture>> getExcludeAlbumList();
//
//    @Headers("Cache-Control: max-stale=3600")
//    @GET("https://raw.githubusercontent.com/gavinxxxxxx/Sensual/master/json/topitme_exclude_item.json")
//    Observable<List<Long>> getExcludeItemList();
//
//    @Headers("Cache-Control: max-stale=3600")
//    @GET("http://api.topitme.com")
//    Observable<Album> getTopitAlbum(
//            @Query("method") String method,
//            @Query("id") long id,
//            @Query("limit") int limit,
//            @Query("offset") long offset);
//
//
//    /* **************************************************************************** *
//     * *********************************** 设置 ************************************ *
//     * **************************************************************************** */
//
//    @Streaming
//    @GET
//    Observable<ResponseBody> download(@Url String url);
//
//    @Headers("Cache-Control: max-stale=2419200")
//    @GET("https://raw.githubusercontent.com/gavinxxxxxx/Sensual/master/json/license.json")
//    Observable<List<License>> getLicense();

}
