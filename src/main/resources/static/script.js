// blir alltid samma spel som körs... :(
// Språk? ENGELSKA eller? 
// TODO: Skicka highscore till API:t, som i sin tur kommunicerar med DB 
// känns dumt att ha massa gobala variabler... 
var questions
var index = -1
var timerId = 0
var answers = []
var timeLeft = -1

$(document).ready(function () { //denna körs varje gång en sida laddas
  $('#result-page').hide();

  // hämta svårighetsgrad
  var difficulty = window.location.pathname.replace("/quiz/", "")
  console.log(difficulty)

  // hämtar frågorna
  $.ajax({
    method: "GET",
    url: "http://localhost:8080/game/" + difficulty,
    headers: { "Accept": "application/json" }
  })
    // kör igång spelet
    .done(function (data) {
      console.log(data);
      questions = data;
      $('#h2-quiz').text(difficulty + " Quiz");
      printQuestion(questions[index]);
    })
});

function printQuestion() {
  // när vi kommit till sista frågan avbryter vi
  if (index == 9) {
    clearTimeout(timerId);
    showResult();
  }

  else{
    index ++
    $('#h4-quiz').text("Question " + (index+1) + "/" + questions.length);
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

// när namnet skickas körs saveToScoreboard 
$("#name-form").submit(saveToScoreboard)

function checkAnswer(event) {
  event.preventDefault() // den gör så att saker funkar 
  var points = timeLeft;

  var answer = $("#answer_input").val(); // hämtar vårt svar
  console.log(questions[index]['answer'])
  $("#answer_input").val('') // tömmer input

  if (answer == questions[index]['answer']) {
    // räknar ut poäng
    if (points > 145) {
      points = points * points / 90;
    }
    else {
      points = points * points / 180;
    }
    // sparar resultat
    var right = {
      question: questions[index]['question'],
      player_points: points,
      answer: questions[index]['answer']
    }
    answers.push(right)
    printQuestion() 
    
    }
};

function startTimer() {
  var timeLeft = 300;
  timerId = setInterval(countdown, 100);

  function countdown() {
    if (timeLeft == -1) {
      // sparar resultat
      var wrong = {
        question: questions[index]['question'],
        player_points: 0,
        answer: questions[index]['answer']
      }
      answers.push(wrong)

      clearTimeout(timerId);
      printQuestion()
    } 
    else {
      // visar bild
      if (timeLeft == 150) {
        $('#img-clue').show();
      }
      $('#timer').text((timeLeft / 10) + ' seconds remaining');
      timeLeft--;
    }
  return timerId
  }
}

function showResult() {
  $('#quiz-page').hide();
  $('#result-page').show();

  var points = countPoints()
  $('#total-score').html("Your score: " + points);

  for (i = 0; i < 10; i++) {
    var item = $(document.createElement('li'));
    $(item).attr("class", "list-group-item");
    $(item).html("Question: " + answers[i]['question'] + "<br> Answer: " + answers[i]['answer'] + "<br> Points: " + answers[i]['player_points']);
    $(item).appendTo(".result-board");
  };

  var difficulty = window.location.pathname.replace("/quiz/", "")
  $("#play-again > a").attr("href", "/quiz/" + difficulty)
};

function countPoints() {
  var points = 0
  for (i = 0; i < 10; i++) {
    points = points + answers[i]['player_points']
  }
  return points
}


function saveToScoreboard(event) {
  event.preventDefault() // den gör så att saker funkar 
  var name = $("#name_input").val();
  var difficulty = window.location.pathname.replace("/quiz/", "")
  var highscore = {
    "name": name, "score": countPoints(), "difficulty": difficulty
  };
  console.log(highscore);
  location.replace("http://localhost:8080/scoreboard")

}

