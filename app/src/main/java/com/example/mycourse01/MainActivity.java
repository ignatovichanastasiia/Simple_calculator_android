package com.example.mycourse01;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
//все элементы системы

    TextView textViewName; //название вверху активити
    EditText editTextNumberFirst; //строка для первого числа
    EditText editTextNumberSecond; //строка для второго числа
    Button buttonPlus; //кнопка +
    Button buttonMinus; //кнопка -
    Button buttonMultiply; //кнопка *
    Button buttonDivide; //кнопка / - деления
    Button buttonC; // кнопка сброса ввода
    Button buttonEq; // кнопка равно
    TextView textResult; // строка для выведения примера и результата
    TextView textAction; // строка для "памяти" - выведение всех действий в калькуляторе
    private double firstNumber; // переменная дабл для первого числа
    private double secondNumber; // переменная дабл для второго числа
    private char calcAction; // переменная -знак(чар) для обозначения действия: сложение\вычитание
    private double result; // переменная дабл для результата вычисления
    String textActionString; // строка в которую плюсуются действия в калькуляторе (аналог истории)



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        textViewName = findViewById(R.id.textViewName);
        editTextNumberFirst = findViewById(R.id.editTextNumberFirst);
        editTextNumberSecond = findViewById(R.id.editTextNumberSecond);
        buttonPlus = findViewById(R.id.buttonPlus);
        buttonMinus = findViewById(R.id.buttonMinus);
        buttonMultiply = findViewById(R.id.buttonMultiply);
        buttonDivide = findViewById(R.id.buttonDivide);
        buttonC = findViewById(R.id.buttonC);
        buttonEq = findViewById(R.id.buttonEq);
        textResult = findViewById(R.id.textResult);
        textAction = findViewById(R.id.textAction);

/* На кнопках +,-,*,/ - одна и та же реакция слушателя.
Присваивается переменной знак действия (плюс или минус и тд)
Если первое число не пустое, то оно парсится в первую переменную.
Иначе остается то, что есть (результат предыдущего вычисления или изначальный 0)
В строку результата кладется первое число и знак действия.
 */
        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcAction = '+';
                if(!editTextNumberFirst.getText().toString().isBlank()) {
                    firstNumber = parseETDouble(editTextNumberFirst);
                }
                textResult.setText(Double.toString(firstNumber)+calcAction);
                }
        });
        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcAction = '-';
                if(!editTextNumberFirst.getText().toString().isBlank()) {
                    firstNumber = parseETDouble(editTextNumberFirst);
                }
                    textResult.setText(Double.toString(firstNumber)+calcAction);
                }
        });
        buttonMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcAction = '*';
                if(!editTextNumberFirst.getText().toString().isBlank()) {
                    firstNumber = parseETDouble(editTextNumberFirst);
                }
                    textResult.setText(Double.toString(firstNumber)+calcAction);
                }
        });
        buttonDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcAction = '/';
                if(!editTextNumberFirst.getText().toString().isBlank()) {
                    firstNumber = parseETDouble(editTextNumberFirst);
                }
                    textResult.setText(Double.toString(firstNumber)+calcAction);
                }
        });

/* На кнопке С очищаются введенные данные, результат.
Переменной знака действия присваивается изначальное "н".
В то же время первое число не обнуляется под капотом.
 */
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextNumberFirst.setText("");
                editTextNumberSecond.setText("");
                textResult.setText("");
                calcAction = 'n';
            }
        });

 /*На кнопке эк (=экуалс), то есть на кнопке равно слушатель проверяетт наличие второго числа в строчке.
 Если его нет, выводит предупреждение.
 На метод калькулятора уходят 2 числа и действие, получается результат
 В полк результата выводится пример и результат.
 В поле экшен (поле "памяти") выводится пример и результат.
 Стираются поля первого и второго чисел.
 Результат присваивается первому числу.
  */
        buttonEq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextNumberSecond.getText().toString().isEmpty()){
                    textViewName.setText("Insert numbers");
                }else {
                    textViewName.setText("Simple Calculator");
                    secondNumber = parseETDouble(editTextNumberSecond);
                    result = calculator(firstNumber, secondNumber, calcAction);
                    textResult.setText(Double.toString(firstNumber) + calcAction + secondNumber + '=' + result);
                    textActionString = "" + firstNumber + calcAction + secondNumber + " = " + result + "\n" + textActionString;
                    textAction.setText(textActionString);
                    editTextNumberFirst.setText("");
                    editTextNumberSecond.setText("");
                    firstNumber = result;
                }

            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        textActionString = "";
        calcAction = 'n';
        firstNumber = 0;
        secondNumber = 0;
        result = 0;
    }


    private double calculator(double firstNumber, double secondNumber, char calcAction){
        try {
            switch (calcAction) {
                case '+':
                    return firstNumber + secondNumber;
                case '-':
                    return firstNumber - secondNumber;
                case '*':
                    return firstNumber * secondNumber;
                case '/':
                    return firstNumber / secondNumber;
                default:
                    return 0;
            }
        }catch(Exception e){
            textAction.setText(e.toString());
        }
        return 0;
    }

    private double parseETDouble(EditText et){
        try{
            return Double.parseDouble(et.getText().toString().trim());
        }catch(Exception e){
            textAction.setText(e.toString());
        }

        return 0;
    }
}