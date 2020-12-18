var questions 
var index = 0 

function startQuiz(difficulty) {
    $.ajax({
      url: "localhost:8080/game/" + difficulty,        
      headers: {"Accept": "application/json"}  
    })

    questions = data

    printQuestion();
    
/* countdown
            var timeLeft = 30;
            var elem = document.getElementsById('timer');
            
            var timerId = setInterval(countdown, 1000);
            
            function countdown() {
              if (timeLeft == -1) {
                clearTimeout(timerId);
                doSomething();
              } 
              else {
                elem.innerHTML = timeLeft + ' seconds remaining';
                timeLeft--;
              }
            }
*/
    
};
function printQuestion(){
    $('#h2-quiz').text(questions[index]['difficulty'] + " Quiz");
    $('.card-text').text(questions[index]['question']);
    $('#img-clue').attr("src", questions[index]['img']); 
    index ++
};

$("#submit_answer").on("click", checkAnswer())

function checkAnswer(){
    console.log("Det funka iaf")
    var answer = $("input").val();
    console.log(answer)
    console.log(questions[index]['answer'])

    if (answer == questions[index]['answer']) {
        printQuestion() //function för att dölja svaret och visa grönt
    }
     
    else {
        $('input:text').focus(
            function(){
                $(this).val('');
            }); 
    }
};
