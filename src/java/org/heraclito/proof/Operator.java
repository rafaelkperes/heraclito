package org.heraclito.proof;



/**
 * List of logic operators.
 * Each operator has a string representation "expression".
 * @author Rafael Koch Peres
 */
public enum Operator {

    /**
     * The NOT operator.
     * Expression: "~".
     */
    NEGATION("~"),
    /**
     * The AND operator.
     * Expression: "^".
     */
    CONJUNCTION("^"),
    /**
     * The OR operator.
     * Expression: "v".
     */
    DISJUNCTION("v"),
    /**
     * The BICONDITIONAL operator.
     * Expression: "<->".
     */
    BICONDITIONAL("<->"),
    /**
     * The IF..THEN operator.
     * Expression: "->".
     */
    IMPLICATION("->"),
    /**
     * The LOGIC HAMMER operator.
     * Expression: "|-".
     */
    LOGIC_HAMMER("|-");

    private final String expression;

    private Operator(String expression) {
        this.expression = expression;
    }

    /**
     * Returns string representing the operator.
     * @return expression representing the operator.
     */    
    public String getExpression() {
        return this.expression;
    }

    /**
     * Returns the expression length of the operator.
     * @return length of operator's expression.
     */
    public Integer getExpressionLength() {
        return this.getExpression().length();
    }
    
    /**
     * Returns string from getExpression method.
     * @return expression representing the operator.
     */
    @Override
    public String toString() {
        return this.getExpression();
    }

}
