package com.example.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//步驟一，把class變成public，並且創建相關的註解
@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

    //步驟二，注入Bean
    @Autowired
    private MockMvc mockMvc;

    //步驟三，建立測試單元
    //在建立MockMvc的時候，主要會分成三個部分

    @Test
    //後面throws Exception是因為下面的perform會去噴出一個Exception，所以才需要在方法上thorw出來
    public void getById() throws Exception{
        /*
        第一個部分就是會去創建一個requestBuilder，
        requestBuilder他會決定要發起的http requst，url路徑
        甚至header，他其實就是一個APItester的概念
         */

        //RequestBuilder也使用了Builder設計模式
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/students/3")
               //等同於 .get("/students/{studentId}",3);
                .header("headerName","headerValue")
                .queryParam("graduate","true");

        /*
        第二個部分就是mockMvc.perform()程式，
        他的用途就是在執行上面的requestBuilder
        這個方法就等同於在APItester按下send
         */

        //mockMvc.perform(requestBuilder) (因加入.and Return而改成下一行）
        MvcResult mvcResult=mockMvc.perform(requestBuilder)
                /*
                第三個部分就是在perform後面的程式，
                andExcept這個方法就是用來驗證結果，
                很像assert的概念
                 */

                //當在.perform下面使用.andDo的時候，就可以輸出這個API的執行結果
                .andDo(print())
                .andExpect(status().is(200))
                /*這邊的equal使用萬用鍵的方式import static method之後在選擇Machers.equalTo
                第一個就是想要取得json object的key,
                ＄的意思就是最外層的json object
                「.」就是所謂的"的"
                */
                .andExpect(jsonPath("$.id",equalTo(3)))
                .andExpect(jsonPath("$.name",notNullValue()))
                .andReturn();

        //使用mvcResult取得responseBody資訊
        String body =mvcResult.getResponse().getContentAsString();
        //顯示
        System.out.println("返回的responseBody為："+ body);

    }

    @Test
    public void create() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/students")
        //到上面都跟get幾乎一樣
        //下面很重要，一定要加上下面這行才可以在requestBody才可以傳遞json參數
                .contentType(MediaType.APPLICATION_JSON);
                //想在request Body的json字串
                //.content(.....)
        //執行
        mockMvc.perform(requestBuilder)
                //查看創建的API的成功http狀態碼決定
                .andExpect(status().is(201));
    }
}