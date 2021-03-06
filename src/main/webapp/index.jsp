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

    <title>Cinema</title>
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
    $(document).ready(function () {
        getOccupiedPlaces();
        setInterval(getOccupiedPlaces, 1000);
    });

    function getOccupiedPlaces() {
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/cinema/hall',
            dataType: 'json'
        }).done(function (data) {
            const places = Array.from(document.getElementsByName('place'));
            for (let ticket of data) {
                for (let p of places) {
                    if (p.value === ticket.row + "/" + ticket.cell) {
                        p.setAttribute("disabled", "disabled");
                        break;
                    }
                }
            }
        }).fail(function (err) {
            console.log(err);
        });
    }

    function selectTicket() {
        const places = Array.from(document.getElementsByName('place'));
        for (let p of places) {
            if (p.checked) {
                localStorage.setItem('ticket', p.value);
                window.location.assign("http://localhost:8080/cinema/payment/")
                break;
            }
        }
    }
</script>

<div class="container">
    <div class="row pt-3">
        <h4>
            ???????????????????????? ?????????? ???? ??????????
        </h4>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th style="width: 120px;">?????? / ??????????</th>
                <th>1</th>
                <th>2</th>
                <th>3</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <th>1</th>
                <td><input type="radio" name="place" value="1/1"> ?????? 1, ?????????? 1</td>
                <td><input type="radio" name="place" value="1/2"> ?????? 1, ?????????? 2</td>
                <td><input type="radio" name="place" value="1/3"> ?????? 1, ?????????? 3</td>
            </tr>
            <tr>
                <th>2</th>
                <td><input type="radio" name="place" value="2/1"> ?????? 2, ?????????? 1</td>
                <td><input type="radio" name="place" value="2/2"> ?????? 2, ?????????? 2</td>
                <td><input type="radio" name="place" value="2/3"> ?????? 2, ?????????? 3</td>
            </tr>
            <tr>
                <th>3</th>
                <td><input type="radio" name="place" value="3/1"> ?????? 3, ?????????? 1</td>
                <td><input type="radio" name="place" value="3/2"> ?????? 3, ?????????? 2</td>
                <td><input type="radio" name="place" value="3/3"> ?????? 3, ?????????? 3</td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="row float-right">
        <button type="button" class="btn btn-success" onclick="selectTicket()">????????????????</button>
    </div>
</div>
</body>
</html>