let websocket = null;

connectWebSocket();

// 监听窗口关闭事件，当窗口关闭时，主动关闭websocket连接
window.onbeforeunload = function () {
    websocket.close();
};

function connectWebSocket() {
    if (websocket !== null) {
        let readyState = websocket.readyState;
        console.log("websocket.readyState: " + readyState);
        if (readyState === WebSocket.CONNECTING || readyState === WebSocket.OPEN) {
            alert("Already Connected");
            return
        }
    }

    // 判断当前浏览器是否支持 WebSocket
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://localhost:8080/websocket");
        bindWebSocketEvent();
    } else {
        alert('WebSocket Not Support');
    }
}

function bindWebSocketEvent() {
    // 连接发生错误的回调方法
    websocket.onerror = function () {
        setMessageInnerHTML("error");
    };

    // 连接成功建立的回调方法
    websocket.onopen = function (event) {
        let status = 'connected';
        console.log(status);
        // 等价于 document.getElementById('status-label').innerHTML = status;
        $('#status-label').html(status);
    };

    // 接收到消息的回调方法
    websocket.onmessage = function (event) {
        let message = event.data;
        console.log("on message: " + message);
        setMessageInnerHTML(message);
    };

    // 连接关闭的回调方法
    websocket.onclose = function () {
        let status = 'closed';
        console.log(status);
        // 等价于 document.getElementById('status-label').innerHTML = status;
        $('#status-label').html(status);
    };
}

/**
 * 将消息显示在网页上
 *
 * @param innerHTML
 */
function setMessageInnerHTML(innerHTML) {
    document.getElementById('message').innerHTML
        += '<hr>' + currentTime() + ': '  + innerHTML;
}

/**
 * 关闭连接
 */
function closeWebSocket() {
    websocket.close();
}

/**
 * 发送消息
 */
function send() {
    let message = document.getElementById('text').value;
    websocket.send(message);
}

/**
 * Current Time
 *
 * @returns {string}
 */
function currentTime() {
    let now = new Date();
    let year = now.getFullYear();
    let month = now.getMonth() + 1;
    let day = now.getDate();
    let hh = now.getHours();
    let mm = now.getMinutes();
    let ss = now.getSeconds();

    let clock = year + "-";
    if(month < 10) clock += "0";
    clock += month + "-";
    if(day < 10) clock += "0";
    clock += day + " ";
    if(hh < 10) clock += "0";
    clock += hh + ":";
    if (mm < 10) clock += '0';
    clock += mm + ":";
    if (ss < 10) clock += '0';
    clock += ss;

    return(clock);
}