// fixa scoreboard till fast bredd m.m.
// specialtecken (mellanslag) , - dubbla mellanslag mellan ord.
// endast ETT resultat per svårighet sparas --> resultat skrivsöver, permanent?
// användarnamn(nickname) sparas inte i listan  
// text första sidan
// API sidan
//ta bort mappen images? 
var questions = []
var index = -1
var timerId = 0
var answers = []
var use_clue = false
var timeLeft = -1 // varför -1? 

$('.start-quiz').click(startQuiz());
$('.scoreboard-page').click(getScoreboard());
$('#show-img').click(function(){
  $('#img-clue').show();
  use_clue = true
});


function startQuiz() { //denna körs när ett spel startas 
  $('#result-page').hide();
  // hämta svårighetsgrad
  var difficulty = window.location.pathname.replace("/quiz/", "")
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
};

function printQuestion() {
  // när vi kommit till sista frågan avbryter vi
  if (index == 9) {
    clearTimeout(timerId);
    showResult();
  }

  else {
    index++
    clearTimeout(timerId);
    startTimer();
    use_clue = false
    $('#h4-quiz').text("Question " + (index + 1) + "/" + questions.length);
    $('.card-text').text(questions[index]['question']);
    $('#img-clue').attr("src", "/images/cat.jpg" ); //questions[index]['image']
    $('#img-clue').hide();
    // tar bort den gamla timern och skapar en ny
    
  }
};

// när svaret ges körs checkAnswer
$("#question-form").submit(checkAnswer)

function checkAnswer(event) {
  event.preventDefault() // den gör så att saker funkar 
  var points = (timeLeft * 10);

  var answer = $("#answer_input").val().toLowerCase().replace(/[^a-z0-9 ]/g, ""); // hämtar spelarens svar   

  console.log(questions[index]['answer'])
  $("#answer_input").val('') // tömmer input

  if (answer == questions[index]['answer']) {
    // räknar ut poäng
    if (use_clue == false) {
      points = points * points / 90;
    }
    else {
      points = points * points / 180;
    }
    // sparar resultat
    var right = {
      question: questions[index]['question'],
      player_points: Math.round(points),
      answer: questions[index]['answer']
    }
    answers.push(right)
    printQuestion()

  }
};

function startTimer() {
  timeLeft = 30;
  timerId = setInterval(countdown, 1000);

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
   // else {
      // visar bild
   //   if (timeLeft == 15) {
   //     $('#img-clue').show();
   //   }
      $('#timer').text(timeLeft + ' seconds remaining');
      timeLeft--;
  //  }
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

$("#name-form").submit(saveToScoreboard) // när namnet skickas körs saveToScoreboard 

function countPoints() {
  var points = 0
  for (i = 0; i < 10; i++) {
    points = points + answers[i]['player_points']
  }
  return points
}

function saveToScoreboard(event) {
  event.preventDefault()

  var score = {
    "nickname": $("#name_input").val(),
    "points": countPoints(),
    "difficulty": window.location.pathname.replace("/quiz/", ""),
  };
  console.log($("#name_input").val(),)

  $.ajax({
    method: "POST",
    url: 'http://localhost:8080/score',
    data: JSON.stringify(score),
    headers: { "Accept": "application/json" }
  })
    .done(function () {
      console.log('Lagt till följande data:');
      console.log(JSON.stringify(score));
      location.replace("http://localhost:8080/scoreboard");
    });
}

function getScoreboard() {
  $.ajax({
    method: "GET",
    url: "http://localhost:8080/highscore?amount=1",
    headers: { "Accept": "application/json" }
  })
    .done(function (data) {
      console.log(data);
      var scoreboards = data;
      showScoreboard(scoreboards)

    })
}

function showScoreboard(scoreboards) {
  for (i = 0; i < 3; i++) {
    var scoreboard = scoreboards[i]
    for (a = 0; a < scoreboard.length; a++) {
      var entry = $(document.createElement('li'));
      $(entry).attr("class", "list-group-item");
      $(entry).appendTo("#scoreboard-" + scoreboard[a]['difficulty']);

      var html = '<p><span class="left"> Hanna och Rebecka</span> <span class="right">' + scoreboard[a]['points'] + '</span></p>'
      $(entry).append(html);

    }
  }
}
