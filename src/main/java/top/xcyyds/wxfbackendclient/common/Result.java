package top.xcyyds.wxfbackendclient.common;

import lombok.Data;

/**
 * @Author: chasemoon
 * @CreateTime: 2025-03-09
 * @Description:统一返回类，返回到前端
 * @Version:
 */
@Data
public class Result<T> {
    /*
     *状态码
     */
    private int code;
    /*
     *成功/失败信息
     */
    private String message;
    /*
     *返回数据
     */
    private T data;

    public Result(int code, String msg, T data) {
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    public Result() {
    }

    /*
     * @Description: 操作执行成功的返回函数
     * @param T
     * @return: Result<T>
     * @Author:  chasemoon
     * @date:  2025/3/12 08:41
     */
    public static <T> Result<T>success(T data){
        Result<T>result =new Result<>();
        result.setCode(200);
        result.setMessage("success");
        result.setData(data);
        return result;
    }
    /*
     * @Description: 操作执行失败的返回函数
     * @param T
     * @return: Result<T>
     * @Author:  chasemoon
     * @date:  2025/3/12 08:41
     */
    public static <T> Result<T>error(){
        // to do 。。。。。。
        return null;
    }
}
