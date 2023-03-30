function send() {

    var message = document.getElementById("message").value;
    $.ajax({
        type: "POST",
        url: "/sendMessage",
        contentType: 'application/json',
        data: JSON.stringify({
            "message": message
        }),
        success: function (result) {
            console.log(result);
            alert(result);
            // $('#picClicksNum').text(parseInt($('#picClicksNum').text())+1);

        },
        error: function (result) {
            alert("错误："+result.toString())
        }
    })
}