package br.studio.quarkus.services.wscalculator;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

import java.util.List;


@WebService
public interface CalculatorService {

    @WebMethod
    public Integer add(Integer a, Integer b);

    @WebMethod
    public Integer subtract(Integer a, Integer b);

    @WebMethod
    public Integer multiply(Integer a, Integer b);

    @WebMethod
    public Integer divide(Integer a, Integer b);

    @WebMethod
    public Result addOperands(Operands operands);

    @WebMethod
    public int addNumberAndOperands(int a, Operands operands);

    @WebMethod
    public int addArray(int... array);

    @WebMethod
    public int addList(List<Integer> list);

}
