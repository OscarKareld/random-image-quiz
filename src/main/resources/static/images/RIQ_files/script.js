// specialtecken ! ? & dubbla mellanslag mellan ord.
// text första sidan
// Vi får previewURL men vill ha webformatURL

var questions = []
var index = -1
var timerId = 0
var answers = []
var use_clue = false
var timeLeft = -1 // varför -1? 

//dropdown
$('#navbarDropdownMenuLink').click(function(){
  if($(".dropdown-menu").is(":visible")){
    $('.dropdown-menu').hide();
  }
  else{
    $('.dropdown-menu').show();
  }
});

//knappar
$('.start-quiz').click(startQuiz());
$('#show-img').click(function(){
  $('#img-clue').show();
  use_clue = true
});
$("#name-form").submit(saveToScoreboard)
$('.scoreboard-page').click(getScoreboard());

function startQuiz() { //denna körs när ett spel startas 
  $('#result-page').hide();
  $('#quiz-page').hide();
  $('#loading').hide();
  // hämta svårighetsgrad
  var difficulty = window.location.pathname.replace("/quiz/", "")
  difficulty.toLowerCase();
  makeRequest();
  var loadingInterval = null
  // hämtar frågorna
  function makeRequest() {
    $.ajax({
      method: "GET",
      url: "http://localhost:8080/game/" + difficulty,
      headers: { "Accept": "application/json" }
    })
      // kör igång spelet
      .done(function (data) {
        // visar laddar-sida
        if(data == null){
          $('#loading').show();
          var i = 0;
          if (!loadingInterval) {
            loadingInterval = setInterval(function(){
              i ++
              var j
              var dots = []

              for (j = 0; j < i; j++) dots.push('.') 
              $('#loading').html("Loading" + dots.join(''));
              i = i % 3
            }, 800)
          } 
          makeRequest();
        }  
        // sätter upp en spelomgång
        else{
          $('#loading').hide();
          $('#quiz-page').show();
          questions = data;
          $('#h2-quiz').text(difficulty + " Quiz");
          printQuestion(questions[index]);
          console.log(data)
          clearInterval(loadingInterval)
        };
      })
  }
};

function printQuestion() {
  // när vi kommit till sista frågan avbryter vi
  if (index == 9) {
    clearTimeout(timerId);
    showResult();
  }
  
  else {
    index++
    // tar bort den gamla timern och skapar en ny
    clearTimeout(timerId);
    startTimer();
    // skriver ut fråga
    use_clue = false
    $('#h4-quiz').text("Question " + (index + 1) + "/" + questions.length);
    $('.card-text').text(questions[index]['question']);
    $('#img-clue').attr("src", questions[index]['image']); ///images/cat.jpg
    $('#img-clue').hide();
  }
};

// när svaret ges körs checkAnswer
$("#question-form").submit(checkAnswer)

function checkAnswer(event) {
  event.preventDefault()
  var points = (timeLeft * 10);

  var answer = $("#answer_input").val().toLowerCase().replace(/[^a-z0-9 ]/g, ""); // hämtar spelarens svar och fixar till det  

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
  timeLeft = 10;
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
      $('#timer').text(timeLeft + ' seconds remaining');
      timeLeft--;
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
    $(item).html("<span class='bold'>Question: </span>" + answers[i]['question'] + "<br> <span class='bold'>Answer: </span> <span class='capital'>" + answers[i]['answer'] + "</span> <br> <span class='bold'>Points: </span>" + answers[i]['player_points']);
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
  event.preventDefault()

  var score = {
    "difficulty": window.location.pathname.replace("/quiz/", ""),
    "points": countPoints(),
    "userNickName": $("#name_input").val(),
  };

  $.ajax({
    method: "POST",
    url: 'http://localhost:8080/score',
    data: JSON.stringify(score),
    headers: { "Accept": "application/json" }
  })
    .done(function () {
      location.replace("http://localhost:8080/scoreboard");
    });
}

function getScoreboard() {
  $.ajax({
    method: "GET",
    url: "http://localhost:8080/highscore?amount=30",
    headers: { "Accept": "application/json" }
  })
    .done(function (data) {
      var scoreboards = data;
      showScoreboard(scoreboards)

    })
}

function showScoreboard(scoreboards) {
  for (i = 0; i < 3; i++) {
    var scoreboard = scoreboards[i]

    for (a = 0; a < scoreboard.length; a++) {
      var entry = $(document.createElement('li'));
      var b = a+1
      $(entry).attr("class", "list-group-item");
      $(entry).appendTo("#scoreboard-" + scoreboard[a]['difficulty']);
      if (a < 3 && a < scoreboard.length)
        $(entry).appendTo("#scoreboard-start-" + scoreboard[a]['difficulty']);
      var html = '<p><span class="bold">'+ b + ". </span>" + scoreboard[a]['userNickName'] + '<span class="right bold">' + scoreboard[a]['points'] + '</span></p>'
      $(entry).append(html);

    }
  }
}
