var questions 
var index = 0 

function startQuiz() {
    var difficulty = window.location.pathname.replace("/quiz/", "")
    $.ajax({
      url: "localhost:8080/game/" + difficulty,        
      headers: {"Accept": "application/json"}  
    })
    
    questions = data
    console.log(questions)
    console.log(questions[index])
    printQuestion(questions[index]);
    
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
function printQuestion(question){
    $('#h2-quiz').text(question['difficulty'] + " Quiz");
    $('.card-text').text(question['question']);
    $('#img-clue').attr("src", question['img']); 
    index ++
};

$("#question-form").submit(checkAnswer)

function checkAnswer(event){
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
