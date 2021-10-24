package com.hdiinfra;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.Stage;
import software.amazon.awscdk.core.StageProps;

public class HdiInfraApiStage extends Stage {

  public HdiInfraApiStage(final Construct scope, final String id) {
    this(scope, id, null);
  }

  public HdiInfraApiStage(final Construct scope, final String id, final StageProps props) {
    super(scope, id, props);

    Stack lambdaStack = new HdiInfraApiStack(this, "HdiInfraApiStack");
  }

}