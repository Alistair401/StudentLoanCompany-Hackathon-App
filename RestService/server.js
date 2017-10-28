var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var sqlite3 = require('sqlite3');

var router = express.Router();

let port = process.env.PORT || 80;
let db = new sqlite3.Database(':memory:', sqlite3.OPEN_READWRITE, function () {
    db.run("CREATE TABLE submissions (name TEXT,birthday TEXT,email TEXT,university TEXT,nationality TEXT,perm_address TEXT, signature BLOB, image BLOB, course TEXT, degree_year INTEGER, bank_number INTEGER, sort_code TEXT, borrow_amount INTEGER, contact_name TEXT, contact_mobile INTEGER)");
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

router.route('/retrieve').get(function (req, res) {
    console.log('DEBUG: accessed \'/retrieve\'');
    console.log('DEBUG: returning all submissions');
    db.all('SELECT * FROM submissions', function (err, rows) {
        res.json(rows)
    });
});

app.use('/', router);

app.listen(port, function () {
    console.log('DEBUG: app started on port ' + port);
});

function submit(form_obj, callback) {
    db.run('INSERT into submissions VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)', [form_obj['name'], form_obj['birthday'], form_obj['email'], form_obj['university'], form_obj['nationality'], form_obj['perm_address'], form_obj['signature'], form_obj['image'], form_obj['course'], form_obj['degree_year'], form_obj['bank_number'], form_obj['sort_code'], form_obj['borrow_amount'], form_obj['contact_name'], form_obj['contact_mobile']], function (err) {
        if (err) {
            console.log('DEBUG: error');
        } else {
            console.log('DEBUG: saved to row ' + this.lastID);
            callback();
        }
    });
}
