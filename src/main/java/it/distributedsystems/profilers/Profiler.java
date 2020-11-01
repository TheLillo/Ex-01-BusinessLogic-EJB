package it.distributedsystems.profilers;


import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Profiler {

    @AroundInvoke
    public Object profiler(InvocationContext ctx){
        Object obj = null;
        try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter("/home/fabbrei/Downloads/log.txt", true));
            writer.write("creation\n");
            writer.write(ctx.getMethod().getName()+"\n");
            writer.write(""+System.currentTimeMillis()+"\n");
            writer.close();
            obj = ctx.proceed();
        }catch (Exception e){

        }

        return obj;

    }

}
