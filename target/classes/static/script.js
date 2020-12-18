//$('form').hide();

function sendAnswer(answer) { // varför skicka med answer?
    return function () {
        var data = {};
        data.answer = $('#question-form input[name=answer]').val();

        $.ajax({
            method: "POST",
            url: 'http://localhost:8080/',
            data: JSON.stringify(data),
            headers: { "Accept": "application/json" }
        })
            .done(function (result) {
                if (response = true) {
                    //show new question, show green/"bock"
                }
                else {
                    // clear form, show red/x
                }
            });
    }
}

$(document).ready(function () {
    $('#submit_answer').click(sendAnswer());
});
