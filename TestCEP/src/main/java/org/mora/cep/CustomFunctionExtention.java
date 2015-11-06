package org.mora.cep;

import org.wso2.siddhi.core.config.SiddhiContext;
import org.wso2.siddhi.core.exception.QueryCreationException;
import org.wso2.siddhi.core.executor.function.FunctionExecutor;
import org.wso2.siddhi.query.api.definition.Attribute;
import org.wso2.siddhi.query.api.extension.annotation.SiddhiExtension;

@SiddhiExtension(namespace = "radar", function = "boundary")
public class CustomFunctionExtention extends FunctionExecutor {
    int matrixSize=240;
    double[][] matrix;
    int minRow=240,maxRow=0,minCol=240,maxCol=0;
    int threshold=1;
    Attribute.Type returnType;

    @Override
    public void init(Attribute.Type[] types, SiddhiContext siddhiContext) {
        matrix=new double[matrixSize][matrixSize];
        for (Attribute.Type attributeType : types) {
            if (attributeType == Attribute.Type.STRING) {
                returnType = attributeType;
                break;
            } else {
                throw new QueryCreationException("file cannot have parameters with types String or Bool");
            }
        }
    }

    @Override
    protected Object process(Object o) {
        String data=o.toString();
        String[] zValues=data.split(",");
        double reflectVal=0;
        int k=1;
        for(int i=0;i<matrixSize;i++){
            for(int j=0;j<matrixSize;j++){
                reflectVal=Double.parseDouble(zValues[k]);
                if(reflectVal>threshold){
                    if(reflectVal>maxRow){
                        maxRow=i;
                    }
                    if(reflectVal<minRow){
                        minRow=i;
                    }
                    if(reflectVal>maxCol){
                        maxCol=j;
                    }
                    if(reflectVal<minCol){
                        minCol=j;
                    }
                }
                k++;
            }
        }
        return minRow+" "+maxRow+" "+minCol+" "+maxCol;
    }

    public void destroy() {
    }

    public Attribute.Type getReturnType() {
        return returnType;
    }

}