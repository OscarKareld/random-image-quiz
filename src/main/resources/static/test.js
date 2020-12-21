var questions;
var index = 0;

$(document).ready(function() {

    var question1 = {}
    question1.difficulty = "Easy";
    question1.question = "Vad heter Hanna?";
    question1.answer = "Hanna";
    question1.img = "/images/cat.jpg"; 

    var question2 = {}
    question2.difficulty = "Easy";
    question2.question = "Vad heter Rebecka?";
    question2.answer = "Rebecka";
    question2.img = "/images/cat.jpg";

    questions = [question1, question2];
    
    printQuestion(questions[index]); 
});

function printQuestion(question){

    $('#h2-quiz').text(question['difficulty'] + " Quiz");
    $('.card-text').text(question['question']);
    $('#img-clue').attr("src", question['img']); 
};

$("#question-form").submit(onSubmit)

function onSubmit(event){
    event.preventDefault()
    console.log("Det funka iaf")
    var answer = $("input").val();
    console.log(answer)
    console.log(questions[index]['answer'])

    if (answer == questions[index]['answer']) {
        index ++
        printQuestion(questions[index]) //function för att dölja svaret och visa grönt
        
    } 
    else {
        $('input:text').focus(
            function(){
                $(this).val('');
            }); 
    }
};
