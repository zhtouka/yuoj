package com.yupi.yuojcodesandbox.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.*;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.PullResponseItem;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.LogContainerResultCallback;

import java.util.List;


public class DockerDemo {

    public static void main(String[] args) throws InterruptedException {
        DockerClient dockerClient = DockerClientBuilder.getInstance().build();
//        PingCmd pingCmd = dockerClient.pingCmd();
//        pingCmd.exec();

        String image = "nginx:latest";
//        PullImageCmd pullImageCmd = dockerClient.pullImageCmd(image);
//        PullImageResultCallback pullImageResultCallback = new PullImageResultCallback() {
//            @Override
//            public void onNext(PullResponseItem item) {
//                System.out.println("item = " + item);
//                super.onNext(item);
//            }
//        };
//        pullImageCmd
//                .exec(pullImageResultCallback)
//                .awaitCompletion();
//        System.out.println("下载完成");

//        CreateContainerCmd containerCmd = dockerClient.createContainerCmd(image);
//        CreateContainerResponse response = containerCmd
//                .withCmd("echo", "Hello, World!")
//                .exec();
//        System.out.println(response);

        ListContainersCmd listContainersCmd = dockerClient.listContainersCmd();
        List<Container> containerList = listContainersCmd
                .withShowAll(true)
                .exec();
        System.out.println("容器数量：" + containerList.size());
        Container container = containerList.get(0);

        dockerClient.startContainerCmd(container.getId())
                .exec();

        LogContainerResultCallback logContainerResultCallback = new LogContainerResultCallback() {
            @Override
            public void onNext(Frame item) {
                System.out.println("日志：" + new String(item.getPayload()));
                super.onNext(item);
            }
        };
        dockerClient.logContainerCmd(container.getId())
                .withStdErr(true)
                .withStdOut(true)
                .exec(logContainerResultCallback).awaitCompletion();

        dockerClient.removeContainerCmd(container.getId())
                .withForce(true)
                .exec();

         containerList = dockerClient.listContainersCmd()
                 .withShowAll(true)
                 .exec();
        System.out.println("容器数量：" + containerList.size());

        dockerClient.removeImageCmd("f5a6b296b8a2")
                .withForce(true)
                .exec();
        List<Image> imageList = dockerClient.listImagesCmd().withShowAll(true).exec();
        System.out.println("镜像数量: " + imageList.size());
    }
}
