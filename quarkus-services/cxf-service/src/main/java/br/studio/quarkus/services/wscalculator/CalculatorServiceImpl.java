package br.studio.quarkus.services.wscalculator;

import jakarta.jws.WebService;

import java.util.List;
import java.util.stream.IntStream;

@WebService(serviceName = "CalculatorService", portName = "Calculator", name = "Calculator", endpointInterface = "br.studio.quarkus.services.wscalculator.CalculatorService")
public class CalculatorServiceImpl implements CalculatorService {

    @Override
    public Integer add(final Integer a, final Integer b) {
        return a + b;
    }

    @Override
    public Integer subtract(Integer a, Integer b) {
        return a - b;
    }

    @Override
    public Integer multiply(Integer a, Integer b) {
        return a * b;
    }

    @Override
    public Integer divide(Integer a, Integer b) {
        if (b == 0) {
            return 0;
        }
        return a / b;

    }

    @Override
    public Result addOperands(Operands operands) {
        return new Result(operands.getA() + operands.getB(), operands);
    }

    @Override
    public int addNumberAndOperands(int a, Operands operands) {
        return a + operands.getA() + operands.getB();
    }

    @Override
    public int addArray(int... array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        return IntStream.of(array).sum();
    }

    @Override
    public int addList(List<Integer> list) {
        return list.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }
}


