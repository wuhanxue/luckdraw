/**
 * 中奖重置
 */
function resetSeat() {
    if(confirm("确认重置?")) {
        $.ajax({
            type: "POST",                       // 方法类型
            url: "/resetSeat", // url
            async: false,                      // 同步：意思是当有返回值以后才会进行后面的js程序
            dataType: "json",
            success: function (result) {
                if (result != undefined && result.status == "success") {
                    alert(result.message);
                }
            },
            error: function (result) {
                alert("服务器异常!")
            }
        })
    }
}