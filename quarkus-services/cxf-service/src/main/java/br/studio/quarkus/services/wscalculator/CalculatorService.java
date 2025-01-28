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


    /**
     * @param operands a non-primitive parameter
     * @return
     */
    @WebMethod
    public Result addOperands(Operands operands);

    /**
     * @param a        the first operand
     * @param operands the second two operands
     * @return sum of a, {@link Operands#getA()} and {@link Operands#getB()}
     */
    @WebMethod
    public int addNumberAndOperands(int a, Operands operands);

    /**
     * @param array an array of numbers to sum
     * @return sum of the given {@code array} elements
     */
    @WebMethod
    public int addArray(int... array);

    /**
     * @param list a list of numbers to sum
     * @return sum of the given {@code list} elements
     */
    @WebMethod
    public int addList(List<Integer> list);

}
