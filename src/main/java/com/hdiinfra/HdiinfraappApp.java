package com.hdiinfra;

import software.amazon.awscdk.core.App;
import software.amazon.awscdk.core.Environment;
import software.amazon.awscdk.core.StackProps;

public class HdiinfraappApp {

  public static void main(final String[] args) {
    App app = new App();

    new HdiinfraappStack(app, "HdiinfraappStack", StackProps.builder()
        .env(Environment.builder()
            .account("102733166919")
            .region("us-west-2")
            .build())
        .build());
    new HdiInfraApiStack(app, "HdiInfraApiStack", StackProps.builder()
        .env(Environment.builder()
            .account("102733166919")
            .region("us-west-2")
            .build())
        .build());
    app.synth();
  }
}
