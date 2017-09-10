/**
 * Created by heiha on 2017/9/10.
 */
$(document).ready(function() {
    $("#hello_button_1").click(function() {
        $.ajax({
            url: "http://localhost:8080/demo/hello",
            type: "GET",
            success: function(result) {
                alert("Result: " + result);
            },
            error: function(errMsg) {
                alert("Error: " + errMsg);
            }
        });
    });
    $("#hello_button_2").click(function() {
        $.get("http://localhost:8080/demo/hello", function(result) {
            alert("Result: " + result);
        })
    });
    $("#hello_button_3").click(function() {
        $.ajax({
            url: "http://localhost:8080/demo/message",
            type: "GET",
            success: function(result) {
                alert("Result: " + result);
            },
            error: function(errMsg) {
                alert("Error: " + errMsg);
            }
        });
    });
});