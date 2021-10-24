package com.hdiinfra;

import java.util.Arrays;
import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.pipelines.CodePipeline;
import software.amazon.awscdk.pipelines.CodePipelineSource;
import software.amazon.awscdk.pipelines.ShellStep;
import software.amazon.awscdk.services.ecr.Repository;

public class HdiinfraappStack extends Stack {

  public HdiinfraappStack(final Construct scope, final String id) {
    this(scope, id, null);
  }

  public HdiinfraappStack(final Construct scope, final String id, final StackProps props) {
    super(scope, id, props);
    CodePipeline pipeline = CodePipeline.Builder.create(this, "pipeline")
        .pipelineName("HdiInfraCICD")
        .synth(ShellStep.Builder.create("Synth")
            .input(CodePipelineSource.gitHub("CliqueAdmin/hdi-infra-cdk", "main"))
            .commands(Arrays.asList("npm install -g aws-cdk", "cdk synth"))
            .build())
        .build();

    Repository.Builder.create(scope, id + "-ecr").repositoryName("hdirepos-cicd").build();
  }


}
