package com.hbw.springbootapplication.entity.scan;

import java.io.Serializable;

public class ScanReceiptRes implements Serializable {
    private WordsResult words_result;
    private Long log_id;
    private Integer  words_result_num;

    private String error_code;

    private String error_msg;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public WordsResult getWords_result() {
        return words_result;
    }

    public void setWords_result(WordsResult words_result) {
        this.words_result = words_result;
    }

    public Long getLog_id() {
        return log_id;
    }

    public void setLog_id(Long log_id) {
        this.log_id = log_id;
    }

    public Integer getWords_result_num() {
        return words_result_num;
    }

    public void setWords_result_num(Integer words_result_num) {
        this.words_result_num = words_result_num;
    }


    @Override
    public String toString() {
        return "ScanReceiptRes{" +
                "words_result=" + words_result +
                ", log_id=" + log_id +
                ", words_result_num=" + words_result_num +
                ", error_code='" + error_code + '\'' +
                ", error_msg='" + error_msg + '\'' +
                '}';
    }
}
