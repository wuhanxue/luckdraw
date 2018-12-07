/**
 * 奖品脚本文件
 *
 * */


function loadPrizeLIst() {
    $('#newPanel').hide();//默认新增编辑面板隐藏
}

function addData() {
    //删除克隆行
    $('#clone').siblings().not($('#plusBtn')).remove();
    $('#newPanel').find("H3").text('奖品添加')//添加标题
   $('#newPanel').show(1000);//新增编辑面板显示
}

function closed() {
    $('#newPanel').hide(1000)//新增编辑面板赢藏
}

function addNewLine(item) {
    var tr=$(item).parent().parent().prev();
    var cloneTr=tr.clone();
    cloneTr.show();
    cloneTr.insertAfter(tr);
    cloneTr.removeAttr('id')
    cloneTr.children().find("input").val("");
    cloneTr.children().find("input:checkbox").prop('checked', false);
      cloneTr.children("td:eq(0)").find("a").remove();
    var delBtn = "<a class='btn btn-default btn-xs' onclick='delLine(this);'><span class='glyphicon glyphicon-minus' aria-hidden='true'></span></a>&nbsp;";
    cloneTr.children("td:eq(0)").prepend(delBtn);

}

function delLine(e) {
    var tr = e.parentElement.parentElement;
    tr.parentNode.removeChild(tr);


}

function save() {

    $('.myclass').each(function () {
        var mode;
        if($(this).children('td').eq(3).find('label').children('input').eq(0).prop('checked')==true){
            mode=1
        }
        if($(this).children('td').eq(3).find('label').children('input').eq(1).prop('checked')==true){
            mode=0
        }
        // if ($(this).children('td').eq(2).find('input').prop('type') != 'text') {
        //     var formFile = new FormData();
        //     var imageFile = $(this).children('td').eq(2).find("input[name='file']")[0].files[0];
        //     formFile.append("imageFile",imageFile);
        // }
        var data={
            name:$(this).children('td').eq(0).find('input').val(),
            number:$(this).children('td').eq(1).find('input').val(),
            mode:mode,
        }
        console.log(data)
        $.ajax({
            type: "POST",                       // 方法类型
            url: "prize", // url
            data:JSON.stringify(data),
            async: false,                      // 同步：意思是当有返回值以后才会进行后面的js程序
            dataType: "json",
            contentType: 'application/json;charset=utf-8',
            success: function (result) {
                if (result != undefined && result.status == "success"){
                    alert(result.message)
                    window.location.reload()
                }
            },
            error: function (result) {
            alert("服务器异常!")
            }
        });
        //添加图片路径
            var imageFile = $(this).children('td').eq(2).find("input[name='file']")[0].files[0];
            if(imageFile!=undefined){
                $(this).children('td').eq(2).find('form').submit();
            }
    })
}


function cutOff(item) {
if(confirm("确认删除?")){
    var id=$(item).parent().prev().html();
    console.log(id);

    $.ajax({
        type: "DELETE",                       // 方法类型
        url: "prize", // url
        data:{"id":id},
        async: false,                      // 同步：意思是当有返回值以后才会进行后面的js程序
        dataType: "json",
        // contentType: 'application/json;charset=utf-8',
        success: function (result) {
            if (result != undefined && result.status == "success"){
                alert(result.message)
                window.location.reload()
            }
        },
        error: function (result) {
            alert("服务器异常!")
        }
    })
}
}