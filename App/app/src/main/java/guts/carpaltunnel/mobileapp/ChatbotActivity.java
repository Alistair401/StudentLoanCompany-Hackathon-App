package guts.carpaltunnel.mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kyle on 28/10/2017.
 */

public class ChatbotActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView mText;
    private TextView text;
    private SpeechRecognizer sr;
    private HashMap<String,String> hm = new HashMap<String,String>(){{
        put("What is an income based loan", "An income based loan is money that you borrowed to help with your studies. \n\nThis could have been a Maintenance Loan to help you with your day-to-day costs, or a Tuition Fee Loan paid by us, on your behalf, to your university or college to cover the costs of your tuition fees. You have to pay these loans back. \n\nThis type of loan is also known as an Income Contingent Repayment (ICR) loan./n/nIf you received any additional grants or bursaries you do not pay these back. )");
        put("Can my income based loan be written off?", "Yes, there are different rules for loan write-off depending on where you normally lived when you entered university or college and when you took out your first loan.\n");
        put("When your loan will be written off England Wales", "In England/Wales, It will be cancelled if you: \n\nTook out your first student loan in or before academic year 2005/06, then it will be cancelled when you turn 65; \n\nTook out your first student loan in or after academic year 2006/07, then it will be cancelled 25 years after you became eligible to repay.");
        put("When your loan will be written off Scotland", "In Scotland, It will be cancelled if you: \n\nTook out your first student loan in or before academic year 2006/07, then it will be cancelled when you turn 65; or\n\nTook out your first student loan in or after academic year 2007/08, then it will be cancelled 35 years after you became eligible to repay");
        put("When your loan will be written off NI Northern Ireland", "In Northern Ireland, It will be cancelled if you: \n\nTook out your first student loan in or before academic year 2005/06, then it will be cancelled when you turn 65; or\n\nTook out your first student loan in or after academic year 2006/07, then it will be cancelled 35 years after you became eligible to repay");
        put("How can I find out how much I still owe", "You can login to your student loan repayment account using the login link on this page, and use the Balance Calculator to work out how much you have left to pay.\n\nYou will need to have details of all deductions that have been taken from your pay; and any repayments you have made to HM Revenue & Customs through Self Assessment, since we were last advised of your repayments through the tax system.\n\nIf you have not logged in to your account before, visit the section about how to use your online account on our website.");
        put("What should I do if I think I have paid off my loan since I received my last statement", "You should contact us with details of all the deductions that have been taken from your pay, or that you have paid to HM Revenue & Customs since the date of your last statement.\n\nIf you have a Plan 1 loan and a Plan 2 loan, and from the information you give us you have repaid one plan type, we’ll request that your employer only makes deductions based on the remaining plan type threshold.");
        put("How do I repay my income based loan?", "Your repayments are normally collected through the tax system by HM Revenue and Customs (HMRC).");
        put("What is Pay As You Earn PAYE", "This is where your employer deducts a student loan repayment directly from your pay slip. For more information, see the section on Repaying through PAYE on our website.");
        put("What if I pay tax through Self Assessment", "If you are self-employed you will be responsible for calculating and paying your student loan repayments to HM Revenue & Customs. For more information, see the section on Repaying through Self Assessment on our website.");
        put("How much do I repay", "You will repay 9% of anything you earn over the income threshold. For example, if you earn £1,650 per month, you would pay 9% of £169, or £15 per month to your student loan.");
        put("Can I make more additional repayments","Yes. You can make additional repayments by credit or debit card at any time, directly to us, by using the Make a Payment service.");
        put("How direct repayments are applied to your balance", "When you make a repayment directly to SLC, the way the money is applied to your balance depends on the status of your account.");
        put("What happens if I change employer", "If you change employer, your new employer may ask which repayment plan type you have, Plan 1 or Plan 2, to set the correct threshold and to work out any repayments deductions based on your income. Your plan type can be found on any recent correspondence.");
        put("How do I repay if I am living overseas", "If you are employed overseas or are outwith the UK tax system you will make student loan repayments directly to us.");
        put("What should I do if I think I have nearly paid off my loan", "You can login to your student loan repayment account using the login link on this page, and use the Balance Calculator to work out how much you have left to pay.\n\nIf you are within a few months of paying your loan off in full, or if you have already paid it off, you should contact us with details of all the repayments you have made since the date of your last statement.");
        put("Why have I been charged interest", "Interest is applied to student loans from the moment you receive your first payment until your loan is paid off in full.");
        put("How and when is the interest rate decided", "The interest rate is set by the Department for Education (DfE) each year. It is normally based on the Retail Price Index each March and takes effect from the September of that year.");

    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        Button speakButton = findViewById(R.id.voice);
        Button submitButton = findViewById(R.id.submit_to_chatbot);

        mText = findViewById(R.id.question);
        text = findViewById(R.id.answer);
        speakButton.setOnClickListener(this);
        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(new listener());

        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                text.setText(getAnswer(mText.getText().toString()));
            }});
    }

    public void onClick(View v) {
        if (v.getId() == R.id.voice) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "voice.recognition.test");

            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
            sr.startListening(intent);
            Log.i("111111", "11111111");
        }
    }

    public String getAnswer(String Question) {
        int maxScore = 0;
        String maxAnswer = "No relevant results found.";
        for(String q : hm.keySet()){
            int score = 0;
            for(String s : Question.split(" ")){
                if(q.contains(s))
                    score++;
            }
            if(score > maxScore) {
                maxScore = score;
                maxAnswer = hm.get(q);
            }
        }
        return maxAnswer;
    }

    class listener implements RecognitionListener {
        public void onReadyForSpeech(Bundle params) {
        }
        public void onBeginningOfSpeech() {
        }
        public void onRmsChanged(float rmsdB) {
        }
        public void onBufferReceived(byte[] buffer) {
        }
        public void onEndOfSpeech() {
        }
        public void onError(int error) {
            mText.setText("error " + error);
        }
        public void onResults(Bundle results) {
            String str = new String();
            ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            if (data.size() > 0)
                str += data.get(0);
            mText.setText(str);
        }
        public void onPartialResults(Bundle partialResults) {
        }
        public void onEvent(int eventType, Bundle params) {
        }
    }
}