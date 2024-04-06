package com.yupi.yuojcodesandbox.utils;

import cn.hutool.core.util.StrUtil;
import com.yupi.yuojcodesandbox.model.ExecuteMessage;
import org.springframework.util.StopWatch;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ProcessUtils {

    public static ExecuteMessage runProcessAndGetMessage(Process runProcess, String opName) {
        ExecuteMessage executeMessage = new ExecuteMessage();

        InputStream inputStream = runProcess.getInputStream();
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            int exitValue = runProcess.waitFor();
            executeMessage.setExitValue(exitValue);
            if (exitValue == 0) {
                System.out.println(opName + "成功");
                // 分批获取进程的正常输出
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String compileOutputLine;
                List<String> outputStrList = new ArrayList<>();
                while ((compileOutputLine = bufferedReader.readLine()) != null) {
                    outputStrList.add(compileOutputLine);
                }
                String outputStr = StrUtil.join("\n", outputStrList);
                executeMessage.setMessage(outputStr);
            } else {
                // 分批获取进程的错误输出
                System.out.println(opName + "失败, 错误码: " + exitValue);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String compileOutputLine;

                List<String> outputStrList = new ArrayList<>();
                while ((compileOutputLine = bufferedReader.readLine()) != null) {
                    outputStrList.add(compileOutputLine);
                }
                String outputStr = StrUtil.join("\n", outputStrList);

                executeMessage.setMessage(outputStr);

                // 分批获取错误流
                InputStream errorStream = runProcess.getErrorStream();
                BufferedReader errorBufferedReader = new BufferedReader(new InputStreamReader(errorStream));
                String errorCompileOutputLine;

                List<String> errorOutputStrList = new ArrayList<>();
                while ((errorCompileOutputLine = errorBufferedReader.readLine()) != null) {
                    errorOutputStrList.add(errorCompileOutputLine);
                }
                String errorOutputStr = StrUtil.join("\n", errorOutputStrList);

                executeMessage.setErrorMessage(errorOutputStr);
                errorStream.close();
            }
            stopWatch.stop();
            executeMessage.setTime(stopWatch.getLastTaskTimeMillis());
            inputStream.close();
            runProcess.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return executeMessage;
    }


    public static ExecuteMessage runInteractProcessAndGetMessage(Process runProcess, String opName, String args) {
        ExecuteMessage executeMessage = new ExecuteMessage();

        try {

            OutputStream outputStream = runProcess.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
            String[] strs = args.split(" ");
//            String join = StrUtil.join("\n", strs) + "\n";
//            outputStreamWriter.write(join);
//            outputStreamWriter.flush();

            for (String s : strs) {
                outputStreamWriter.write(s + "\n");
                outputStreamWriter.flush();
            }

            int exitValue = runProcess.waitFor();
            executeMessage.setExitValue(exitValue);
            InputStream inputStream = runProcess.getInputStream();
            if (exitValue == 0) {
                System.out.println(opName + "成功");
                // 分批获取进程的正常输出
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String compileOutputLine;
                StringBuilder compileOutputStringBuilder = new StringBuilder();
                while ((compileOutputLine = bufferedReader.readLine()) != null) {
                    compileOutputStringBuilder.append(compileOutputLine);
                }
                executeMessage.setMessage(compileOutputStringBuilder.toString());
            } else {
                // 分批获取进程的正常输出
                System.out.println(opName + "失败, 错误码: " + exitValue);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
                String compileOutputLine;
                StringBuilder compileOutputStringBuilder = new StringBuilder();
                while ((compileOutputLine = bufferedReader.readLine()) != null) {
                    compileOutputStringBuilder.append(compileOutputLine);
                }
                executeMessage.setMessage(compileOutputStringBuilder.toString());

                // 分批获取错误流
                BufferedReader errorBufferedReader = new BufferedReader(new InputStreamReader(runProcess.getErrorStream()));
                String errorCompileOutputLine;
                StringBuilder errorCompileOutputStringBuilder = new StringBuilder();
                while ((errorCompileOutputLine = errorBufferedReader.readLine()) != null) {
                    errorCompileOutputStringBuilder.append(errorCompileOutputLine);
                }
                executeMessage.setErrorMessage(errorCompileOutputStringBuilder.toString());
            }
            outputStreamWriter.close();
            outputStream.close();
            inputStream.close();
            runProcess.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return executeMessage;
    }

}
