// känns dumt att ha massa gobala variabler...
var questions 
var index = -1
var timerId = 0
var answers = [] 

$(document).ready(function() { //denna körs varje gång en sida laddas, det vill vi inte
    // hämta svårighetsgrad
    var difficulty = window.location.pathname.replace("/quiz/", "")
    console.log(difficulty)
    // hämtar frågorna
    $.ajax({
      method: "GET",
      url: "http://localhost:8080/game/" + difficulty,        
      headers: {"Accept": "application/json"}  
    })
    // kör igång spelet
    .done(function (data) { 
    console.log(data);
    questions = data;
    $('#h2-quiz').text(difficulty + " Quiz");
    printQuestion(questions[index]);
    })  
});


function printQuestion(){
  if (index == 7){ // när vi kommit till sista frågan avbryter vi
    showResult()
    //location.replace("http://localhost:8080/result")
  }

  else{
    index ++
    $('#h4-quiz').text("Question" + (index+1) + "/8");
    $('.card-text').text(questions[index]['question']);
    $('#img-clue').attr("src", "/images/cat.jpg"); 
    $('#img-clue').hide();
    // tar bort den gamla timern och skapar en ny
    clearTimeout(timerId); 
    startTimer();
  }
};
// när svaret ges körs checkAnswer
$("#question-form").submit(checkAnswer)

function checkAnswer(event){
    event.preventDefault() // den gör så att saker funkar, fattar inte riktigt hur dock...

    var answer = $("input").val(); // hämtar vårt svar
    console.log(questions[index]['answer'])

    $("#answer").val('') // tömmer input

    if (answer == questions[index]['answer']) {  
      // sparar resultat
      var right = { question : questions[index]['question'],
                    player_answer : "right", 
                    answer : questions[index]['answer'] }
      answers.push(right)

      printQuestion() 
    
    }
};

function startTimer(){
  var timeLeft = 30;
  timerId = setInterval(countdown, 1000);
  
  function countdown() {
    if (timeLeft == -1) {
      // sparar resultat
      var wrong = { question : questions[index]['question'],
                    player_answer : "wrong", 
                    answer : questions[index]['answer'] }
      answers.push(wrong)

      clearTimeout(timerId);
      printQuestion()
    } 
    else {
      if (timeLeft == 15) {
        $('#img-clue').show(); 
      }
      $('#timer').text(timeLeft + ' seconds remaining');
      timeLeft--;
    }
  return timerId
  }
}

function showResult(){
  console.log(answers)
}
