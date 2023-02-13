// variables
let userName = null;
let state = 'SUCCESS';

// functions
function Message(arg) {
    this.text = arg.text;
    this.message_side = arg.message_side;

    this.draw = function (_this) {
        return function () {
            let $message;
            $message = $($('.message_template').clone().html());
            $message.addClass(_this.message_side).find('.text').html(_this.text);
            $('.messages').append($message);

            return setTimeout(function () {
                return $message.addClass('appeared');
            }, 0);
        };
    }(this);
    return this;
}

function getMessageText() {
    let $message_input;
    $message_input = $('.message_input');
    return $message_input.val();
}

function sendMessage(text, message_side) {
    let $messages, message;
    $('.message_input').val('');
    $messages = $('.messages');
    message = new Message({
        text: text,
        message_side: message_side
    });
    message.draw();
    $messages.animate({scrollTop: $messages.prop('scrollHeight')}, 300);
}

function greet() {
     setTimeout(function () {
        return sendMessage("개미집 데모에 오신걸 환영합니다.", 'left');
    }, 1000);
        setTimeout(function () {
            return sendMessage("당기순이익, 매출액 등 궁금한 점을 물어보세요!", 'left');
        }, 1000);
    setTimeout(function () {
        return sendMessage("사용할 닉네임을 알려주세요.", 'left');
    }, 1000);
}

function onClickAsEnter(e) {
    if (e.keyCode === 13) {
        onSendButtonClicked()
    }
}

function setUserName(username) {

    if (username != null && username.replace(" ", "" !== "")) {
        setTimeout(function () {
            return sendMessage("반갑습니다." + username + "님. 닉네임이 설정되었습니다.", 'left');
        }, 1000);
        setTimeout(function () {
            return sendMessage(username + "님. 조회하실 회사와 지표를 입력해주세요.", 'left');
        }, 1000);

        return username;

    } else {
        setTimeout(function () {
            return sendMessage("올바른 닉네임을 이용해주세요.", 'left');
        }, 1000);

        return null;
    }
}

function onSendButtonClicked() {
    let messageText = getMessageText();
    sendMessage(messageText, 'right');

    if (userName == null) {
        userName = setUserName(messageText);

    } else {
        if (messageText.includes('안녕')) {
            setTimeout(function () {
                return sendMessage("안녕하세요구르트.", 'left');
            }, 1000);
        } else if (messageText.includes('고마')) {
            setTimeout(function () {
                return sendMessage("천만에백만에억만에", 'left');
            }, 1000);
        } else if (messageText.includes('없어')) {
            setTimeout(function () {
                return sendMessage("그렇군!", 'left');
            }, 1000);
         } else if (messageText.includes('선영')) {
            setTimeout(function () {
                return sendMessage("선영으로 이행시 해볼게요.\n 선 영회귀\n 영 원해", 'left');
            }, 1000);
        } else if (messageText.includes('다나')) {
            setTimeout(function () {
                return sendMessage("다나로 이행시 해볼게요.\n 다 나가\n 나 가래", 'left');
            }, 1000);
        }else if (messageText.includes('준호')) {
            setTimeout(function () {
                return sendMessage("준호로 이행시 해볼게요.\n 준 비하시고\n 호 ㅏ이팅", 'left');
            }, 1000);
        } else if (messageText.includes('지수')) {
            setTimeout(function () {
                return sendMessage("지수로 이행시 해볼게요.\n 지 수는\n 수 박 싫어 귤 좋아", 'left');
            }, 1000);
        }  else if (messageText.includes('은진')) {
            setTimeout(function () {
                return sendMessage("은진으로 이행시 해볼게요.\n 은 니은니\n 진 짜 좋아해🧡", 'left');
            }, 1000);
        }  else if (messageText.includes('종은')) {
            setTimeout(function () {
                return sendMessage("종은으로 이행시 해볼게요.\n 종 말종말\n 은 니가 최고야🧡", 'left');
            }, 1000);
        }  else if (messageText.includes('해정')) {
            setTimeout(function () {
                return sendMessage("해정으로 이행시 해볼게요.\n 해 이~ 유~\n 정 신차려~🧡 from선영", 'left');
            }, 1000);
        } else if (messageText.includes('지표')) {
            setTimeout(function () {
                return sendMessage("매출액, 영업이익, 당기순이익, 영업이익률, 순이익률, ROE(자기자본이익률), 부채비율, 당좌비율, 유보율, EPS(주당순이익), PER(주가수익비율), BPS(주당순자산가치), PBR(주가순자산비율), 주당배당금, 시가배당률, 배당성향", 'left');
            }, 1000);
        } else if (messageText.includes('회사이름')) {
            setTimeout(function () {
                return sendMessage("KOSPI200 안에 있는 회사들만 조회 가능합니다. 인터넷을 참고하세요.", 'left');
            }, 1000);
        }else {
            return requestChat(userName,messageText);
        } 
    }
}

function requestChat(userName,messageText) {
    $.ajax({
        type: "POST",
        url: "http://192.168.10.196:5000/predict",
        data: messageText,
        dataType : 'json',
        success: function (res) {
            if (state === 'SUCCESS') {
                return sendMessage(userName + "님, " + res['prediction'], 'left');
            }  else {
                return sendMessage("모르겠다구리.", 'left');
            }
        },
        error: function (request, status, error) {
            console.log(error);
            return sendMessage('서버 오류입니다.', 'left');
        }
    });
}






