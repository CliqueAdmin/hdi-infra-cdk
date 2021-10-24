package com.hdiinfra;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.ecr.Repository;

public class HdiInfraApiStack extends Stack {
  public HdiInfraApiStack(final Construct scope, final String id) {
    this(scope, id, null);
  }

  public HdiInfraApiStack(final Construct scope, final String id, final StackProps props) {
    super(scope, id, props);

    Repository.Builder.create(scope, id + "-ecr").repositoryName("hdirepos-cicd").build();

  }
}
