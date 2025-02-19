package br.studio.quarkus.services.wscalculator;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "result", propOrder = {
    "operands",
    "result",
    "even",
    "theAnswer"
})
public class Result {

    private int result;
    private boolean even;
    @XmlElement(defaultValue = "42")
    private String theAnswer;
    private Operands operands;

    public Result() {
    }

    public Result(int result, Operands operands) {
        super();
        this.result = result;
        this.operands = operands;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public boolean isEven() {
        return even;
    }

    public void setEven(boolean even) {
        this.even = even;
    }

    public String getTheAnswer() {
        return theAnswer;
    }

    public void setTheAnswer(String theAnswer) {
        this.theAnswer = theAnswer;
    }

    public Operands getOperands() {
        return operands;
    }

    public void setOperands(Operands operands) {
        this.operands = operands;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Result result1 = (Result) o;
        return result == result1.result && Objects.equals(operands, result1.operands);
    }

    @Override
    public int hashCode() {
        return Objects.hash(result, operands);
    }
}
