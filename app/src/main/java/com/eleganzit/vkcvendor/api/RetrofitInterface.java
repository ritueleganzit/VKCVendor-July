package com.eleganzit.vkcvendor.api;



import com.eleganzit.vkcvendor.model.AddHourWiseResponse;
import com.eleganzit.vkcvendor.model.AllHourlyDetail.AllHourlyDetailResponse;
import com.eleganzit.vkcvendor.model.CheckCompletePo;
import com.eleganzit.vkcvendor.model.ListSlotsResponse;
import com.eleganzit.vkcvendor.model.LoginRespose;
import com.eleganzit.vkcvendor.model.OTPResponse;
import com.eleganzit.vkcvendor.model.article.ArticleResponse;
import com.eleganzit.vkcvendor.model.articlemap.MapArticleResponse;
import com.eleganzit.vkcvendor.model.defect.DefectArtResponse;
import com.eleganzit.vkcvendor.model.grid.SingleArtResponse;
import com.eleganzit.vkcvendor.model.griddata.POGridResponse;
import com.eleganzit.vkcvendor.model.line.LineResponse;
import com.eleganzit.vkcvendor.model.plan.PlanResponse;
import com.eleganzit.vkcvendor.model.poline.ArtLineResponse;
import com.eleganzit.vkcvendor.model.poline.POLineResponse;
import com.eleganzit.vkcvendor.model.searchCompletedPO.SearchCompletedPOResponse;
import com.eleganzit.vkcvendor.model.searcharticle.SerchArticleListResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by eleganz on 30/4/19.
 */

public interface RetrofitInterface {
    @FormUrlEncoded()
    @POST("/VKC-API/addFeedback")
    Call<LoginRespose> addFeedback(
            @Field("vendor_id") String vendor_id,
            @Field("message") String message,
            @Field("date_time") String date_time

    );

    @FormUrlEncoded()
    @POST("/VKC-API/listSlots")
    Call<ListSlotsResponse> listSlots(
            @Field("date") String formattedDate,
            @Field("vendor_id") String vendor_id,
            @Field("item") String item,
            @Field("article") String article,
            @Field("pur_doc_num") String pur_doc_num

    );
    @FormUrlEncoded()
    @POST("/VKC-API/getAllHourlyDetail")
    Call<AllHourlyDetailResponse> getAllHourlyDetail(
            @Field("pur_doc_num") String pur_doc_num
    );

    @FormUrlEncoded()
    @POST("/VKC-API/resetVendorPassowrd")
    Call<OTPResponse> resetVendorPassowrd   (
            @Field("password") String send_code,
            @Field("email_id") String email_id


    );
    @FormUrlEncoded()
    @POST("/VKC-API/checkCodeVendor")
    Call<OTPResponse> checkCodeVendor   (
            @Field("send_code") String send_code,
            @Field("email_id") String email_id


    );


    @GET("/Vkc-AdminPanel/sendCodeVendor.php")
    Call<OTPResponse> sendCode(
            @Query("email_id") String email_id


    );
    @FormUrlEncoded()
    @POST("/VKC-API/loginVendor")
    Call<LoginRespose> loginUser(
            @Field("email_id") String email_id,
            @Field("password") String password


    );

    @FormUrlEncoded()
    @POST("/VKC-API/getAllPoDetail")
    Call<PlanResponse> getAllPoDetail(
            @Field("vendor_id") String vendor_id


    );
    @FormUrlEncoded()
    @POST("/VKC-API/addMarkCompleteDetail")
    Call<AddHourWiseResponse> addMarkCompleteDetail(
            @Field("item") String item,
            @Field("pur_doc_num") String pur_doc_num,
            @Field("line_id") String line_id,
            @Field("article") String article,
            @Field("grid_value") String grid_value,
            @Field("quality_produced") String quality_produced

    );
    @FormUrlEncoded()
    @POST("/VKC-API/getVendorLine")
    Call<LineResponse> getVendorLine(
            @Field("vendor_id") String vendor_id


    ); @FormUrlEncoded()
    @POST("/VKC-API/updateHourslot")
    Call<OTPResponse> updateHourslot(
            @Field("slot_id") String slot_id


    );
@FormUrlEncoded()
    @POST("/VKC-API/getArticleList")
    Call<ArticleResponse> getArticleList(
            @Field("vendor_id") String vendor_id,
            @Field("pur_doc_num") String pur_doc_num


    );
@FormUrlEncoded()
    @POST("/VKC-API/addAssignLine")
    Call<MapArticleResponse> addAssignLine(
            @Field("article") String article,
            @Field("item") String item,
            @Field("line_id") String line_id,
            @Field("pur_doc_num") String pur_doc_num,
            @Field("number_of_stichers") String number_of_stichers,
            @Field("number_of_helpers") String number_of_helpers


    );


@FormUrlEncoded()
    @POST("/VKC-API/assignLine")
    Call<POLineResponse> assignLine(
            @Field("vendor_id") String vendor_id,
            @Field("pur_doc_num") String pur_doc_num



    );
@FormUrlEncoded()
    @POST("/VKC-API/assignArticle")
    Call<ArtLineResponse> assignArticle(
            @Field("line_id") String line_id,
             @Field("pur_doc_num") String pur_doc_num



    );

    @FormUrlEncoded()
        @POST("/VKC-API/defectEntry")
        Call<DefectArtResponse> defectEntry(
                @Field("article") String article,
                @Field("vendor_id") String vendor_id,
                @Field("item") String item



        );

@FormUrlEncoded()
        @POST("/VKC-API/getHourswiseEntry")
        Call<SingleArtResponse> getHourswiseEntry(
                @Field("pur_doc_num") String pur_doc_num,
                @Field("article") String article,
                @Field("item") String item



        );

    @FormUrlEncoded()
    @POST("/VKC-API/searchcompletedPO")
    Call<SearchCompletedPOResponse> searchcompletedPO(
            @Field("start_date") String start_date,
            @Field("end_date") String end_date,
            @Field("vendor_id") String vendor_id

    );

    @FormUrlEncoded()
    @POST("/VKC-API/addHourlyArticleData")
    Call<AddHourWiseResponse> addHourlyArticleData(
            @Field("po_doc_num") String po_doc_num,
            @Field("line_id") String line_id,
            @Field("item") String item,

            @Field("article") String article,
            @Field("start_time") String start_time,
            @Field("end_time") String end_time,
            @Field("grid_value") String grid_value,
            @Field("quality_produced") String quality_produced,
            @Field("mark_as_complated") String mark_as_complated

    );

    @FormUrlEncoded()
    @POST("/VKC-API/serchArticleList")
    Call<SerchArticleListResponse> serchArticleList(
            @Field("vendor_id") String vendor_id

    ); @FormUrlEncoded()
    @POST("/VKC-API/checkCompletePo")
    Call<CheckCompletePo> checkCompletePo(
            @Field("pur_doc_num") String pur_doc_num

    );
    @FormUrlEncoded()
    @POST("/VKC-API/completedHourlyGrid")
    Call<POGridResponse> pendingGrid   (
            @Field("pur_doc_num") String pur_doc_num,
            @Field("article") String article


    ); @FormUrlEncoded()
    @POST("/VKC-API/completedHourlyGrid")
    Call<POGridResponse> completedHourlyGrid   (
            @Field("pur_doc_num") String pur_doc_num,
            @Field("item") String item,
            @Field("article") String article


    );


}
