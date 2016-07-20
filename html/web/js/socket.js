var socket;
//群组ID
var groupId = 1;
$(function () {
    setTimeout(function () {
        socket = new WebSocket("ws://feedback.snh48.com:8080/websocket");
        // socket = new WebSocket("ws://192.168.4.115:8080/websocket");
        socket.binaryType = "arraybuffer"; // We are talking binary

        socket.onopen = function () {
            var msg = chat.joinGroup(groupId);
            socket.send(msg.toArrayBuffer());
        };

        socket.onclose = function () {
            var msg = chat.leaveGroup(groupId);
            socket.send(msg.toArrayBuffer());
        };

        socket.onmessage = function (evt) {
            try {
                // Decode the Message
                var msg = ChatMessage.decode(evt.data);
                console.log(msg);
                console.log("Received: " + msg.content + " " + msg.timestamp + "\n");
                showMessage(msg);
            } catch (err) {
                console.log("Error: " + err + "\n");
            }
        };
    }, 1000);
});

/**
 * 发送消息
 * @param groupId 群组ID
 * @param content 内容
 * @param pictures 图片字符串以逗号分隔
 */
function sendMessage(groupId, content, pictures) {
    if (socket.readyState == WebSocket.OPEN) {
        var msg = chat.createMsg(groupId, content, pictures);

        //console.log(msg);
        socket.send(msg.toArrayBuffer());
        console.log("Sent: " + msg.content + "\n");
    } else {
        console.log("Not connected\n");
    }
}

/**
 * 显示消息
 * @param chatMessage 消息内容
 */
function showMessage(chatMessage) {
    var html = '';
    var isMyself = user.userId == chatMessage.userId;
    if (isMyself) {
        //自己的消息
        html += '<div class="message me">';
    } else {
        //其他人的消息
        html += '<div class="message">';
    }
    var pic = chatMessage.pic || 'img/ayato.jpeg';
    html += '<img class="avatar" src="' + pic + '"/>';
    html += '<div class="content">';
    html += '<div class="nickname">' + chatMessage.nickname + '<span class="time">' + chat.parseTime(chatMessage.timestamp) + '</span></div>';
    if (isMyself) {
        html += '<div class="bubble bubble_primary right';
    } else {
        html += '<div class="bubble bubble_default left';
    }
    var pictures = chatMessage.pictures;
    var audios = chatMessage.audios;
    var videos = chatMessage.videos;
    if (pictures || audios || videos) {
        html += 'no_arrow">';
        html += '<div class="bubble_cont">';
        html += '<div class="picture">';
        var arr;
        if (pictures) {
            arr = pictures.split(',');
            html += '<img class="msg-img" src="' + arr[0] + '">';
        } else if (audios) {
            arr = audios.split(',');
            html += '<audio class="msg-img" src="' + arr[0] + '">';
        } else if (videos) {
            arr = videos.split(',');
            html += '<video class="msg-img" src="' + arr[0] + '">';
        }
    } else {
        html += '">';
        html += '<div class="bubble_cont">';
        html += '<div class="plain">';
        html += '<pre>' + chatMessage.content + '</pre>';
    }
    html += '</div>';
    html += '</div>';
    html += '</div>';
    html += '</div>';
    html += '</div>';
    $('#messageList').append(html);
    resetMessageArea();
}