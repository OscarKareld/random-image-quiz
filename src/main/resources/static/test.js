$(document).ready(function () {

    var obj1 = {
        "question": "a question",
        "user_answer": "right",
        "answer": "the right answer"
    };

    var obj2 = {
        "question": "another question",
        "user_answer": "wrong",
        "answer": "hello"
    };

    var obj3 = {
        "question": "last question",
        "user_answer": "right",
        "answer": "bye"
    };


    answers = [obj1, obj2, obj3];
    printResult(answers)

});

function printResult(answers) {
    for (i = 0; i < 3; i++) {
        console.log("hej");
        console.log(answers[i]['question']);

        var item = $(document.createElement('li'));
        $(item).attr("class", "list-group-item");
        $(item).html(answers[i]['question'] + ": " + answers[i]['answer'] + answers[i]['user_answer']);
        $(item).appendTo(".result-board");
    };
};
