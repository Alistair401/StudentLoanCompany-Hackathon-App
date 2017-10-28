var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var sqlite3 = require('sqlite3');

var router = express.Router();

let port = 8080;
let db = new sqlite3.Database(':memory:', sqlite3.OPEN_READWRITE, function () {
    db.run("CREATE TABLE submissions (name TEXT,birthday TEXT,email TEXT,university TEXT,nationality TEXT,perm_address TEXT)");
});

app.use(bodyParser.json());

router.use(function (req, res, next) {
    next();
})

router.route('/submit').post(function (req, res) {
    console.log('DEBUG: accessed \'/submit\'');
    console.log('DEBUG: saving form submission');
    submit(req.body, function () {
        console.log('DEBUG: success')
        res.sendStatus(200);
    });
});

app.use('/', router);

app.listen(port);
console.log('DEBUG: app started on port ' + port);

function submit(form_obj, callback) {
    db.run('INSERT into submissions VALUES (?,?,?,?,?,?)', [form_obj['name'], form_obj['birthday'], form_obj['email'], form_obj['university'], form_obj['nationality'], form_obj['perm_address']], function (err) {
        if (err) {
            console.log('DEBUG: error');
        } else {
            console.log('DEBUG: saved to row ' + this.lastID);
            callback();
        }
    });
}
