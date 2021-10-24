package com.hdiinfra;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.ecr.Repository;
import software.amazon.awscdk.services.ecr.RepositoryProps;

public class HdiInfraApiStack extends Stack {

  public HdiInfraApiStack(final Construct scope, final String id) {
    this(scope, id, null);
  }

  public HdiInfraApiStack(final Construct scope, final String id, final StackProps props) {
    super(scope, id, props);
    new Repository(this, id + "-ecr", RepositoryProps.builder().repositoryName("hdirepos-cicd").build());

  }
}
