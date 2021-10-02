<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <title>Ticket booking</title>
</head>
<body>
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>

<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script>
    let interval;
    $(document).ready(function () {
        document.querySelector("body > div > div.row.pt-3 > h3").innerHTML
            = "Вы выбрали ряд "
            + localStorage.getItem("ticket").split("/")[0] + " место "
            + localStorage.getItem("ticket").split("/")[1] + ", Сумма : 500 рублей.";
        checkBusyTicket();
        interval = setInterval(checkBusyTicket, 500);
    });

    function checkBusyTicket() {
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/cinema/hall',
            dataType: 'json'
        }).done(function (data) {
            for (let ticket of data) {
                let baseTicket = ticket.row + "/" + ticket.cell;
                let localTicket = localStorage.getItem("ticket").split("/")[0] + "/"
                    + localStorage.getItem("ticket").split("/")[1];
                if (baseTicket === localTicket) {
                    clearInterval(interval);
                    busyTicket();
                    break;
                }
            }
        }).fail(function (err) {
            console.log(err);
        });
    }

    function sendTicket() {
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/cinema/hall',
            data: JSON.stringify({
                row: localStorage.getItem("ticket").split("/")[0],
                cell: localStorage.getItem("ticket").split("/")[1]
            }),
            dataType: 'json'
        }).done(function (data) {
            if (data.status === 409) {
                busyTicket();
            } else {
                clearInterval(interval);
                alert("Билет успешно забронирован")
                window.location.href = "http://localhost:8080/cinema";
            }
        }).fail(function (err) {
            console.log(err);
        });
    }

    function busyTicket() {
        alert("Извините, место уже занято. Выбирите другое место.");
        window.location.href = "http://localhost:8080/cinema";
    }
</script>

<div class="container">
    <div class="row pt-3">
        <h3>
            Вы выбрали ряд 1 место 1, Сумма : 500 рублей.
        </h3>
    </div>
    <div class="row">
        <form>
            <div class="form-group">
                <label for="username">ФИО</label>
                <input type="text" class="form-control" id="username" placeholder="ФИО">
            </div>
            <div class="form-group">
                <label for="phone">Номер телефона</label>
                <input type="text" class="form-control" id="phone" placeholder="Номер телефона">
            </div>
            <button type="button" class="btn btn-success" onclick="sendTicket()">Оплатить</button>
        </form>
    </div>
</div>
</body>
</html>