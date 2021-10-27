package com.hdiinfra;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.ecr.Repository;
import software.amazon.awscdk.services.ecr.RepositoryProps;
import software.amazon.awscdk.services.ecs.Cluster;
import software.amazon.awscdk.services.ecs.ContainerImage;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedFargateService;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedTaskImageOptions;
import software.amazon.awscdk.services.elasticloadbalancingv2.HealthCheck;

public class HdiInfraApiStack extends Stack {

  public HdiInfraApiStack(final Construct scope, final String id) {
    this(scope, id, null);
  }

  public HdiInfraApiStack(final Construct scope, final String id, final StackProps props) {
    super(scope, id, props);
    Repository repository = new Repository(this, id + "-ecr",
        RepositoryProps.builder().repositoryName("hdirepos-cicd").build());

    // This will create a VPC with a NAT-gateway, IGW, default routes and route tables, and a private and public subnet in all availability zones
    Vpc vpc = new Vpc(this, "hdi-cicd-vpc");

    // Create the ECS cluster
    Cluster cluster = Cluster.Builder.create(this, "hdi-cici-ecs-cluster").vpc(vpc).build();

    // Create a load-balanced Fargate service and make it public
    ApplicationLoadBalancedFargateService fargateService =
        ApplicationLoadBalancedFargateService.Builder.create(this, "hdi-cicd-ecs-svc")
            .cluster(cluster)
            .cpu(
                1024)
            .memoryLimitMiB(2048) // Default is 512
            .desiredCount(2) // Default is 1
            .taskImageOptions(ApplicationLoadBalancedTaskImageOptions.builder()
                .image(ContainerImage.fromEcrRepository(repository, "latest"))
                .containerPort(8080)// The default is port 80, The Spring boot default port is 8080
                .build())
            .publicLoadBalancer(true) // Default is false
            .assignPublicIp(false) // If set to true, it will associate the service to a public subnet
            .build();

    // For our Spring boot application, we need to configure the health check
    fargateService.getTargetGroup().configureHealthCheck(HealthCheck.builder()
        .healthyHttpCodes("200") // Specify which http codes are considered healthy
        // The load balancer REQUIRES a healthcheck endpoint to determine the state of the app.
        // In this example, we're using the Spring Actuator. Configure this in your app if missing.
        .path("/traits")
        .port("8080") // The default is port 80
        .build());
  }
}
