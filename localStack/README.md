# Instalação LocalStack

Para facilitar desenvolvimento local vamos usar o [LocalStack](https://github.com/localstack/localstack), que é um emulador de services cloud da AWS. Com ele é possível executar alguns serviços AWS localmente, na versão Community podemos usar os seguintes serviços

| Service                  | Status    |
|--------------------------|-----------|
| acm                      | available |
| apigateway               | available |
| acm                      | available |
| apigateway               | available |
| cloudformation           | available |
| cloudwatch               | available |
| config                   | available |
| dynamodb                 | available |
| dynamodbstreams          | available |
| ec2                      | available |
| es                       | available |
| events                   | available |
| firehose                 | available |
| iam                      | available |
| kinesis                  | available |
| kms                      | available |
| lambda                   | available |
| logs                     | available |
| opensearch               | available |
| redshift                 | available |
| resource-groups          | available |
| resourcegroupstaggingapi | available |
| route53                  | available |
| route53resolver          | available |
| s3                       | available |
| secretsmanager           | available |
| ses                      | available |
| sns                      | available |
| sqs                      | available |
| ssm                      | available |
| stepfunctions            | available |
| sts                      | available |
| support                  | available |
| swf                      | available |

Para facilitar vamos usar o pip para instalar o LocalStack.

## Requisitos

* `python` (Python 3.6 up to 3.9 supported)
* `pip` (Python package manager)
* `Docker`

## Instalação

```
pip install localstack
```

Ele instala o `localstack-cli` que é usado para executar a imagem do Docker que hospeda o tempo de execução do LocalStack.

### Exemplo

Inicie o LocalStack dentro de um contêiner do Docker executando:

```
% localstack start -d
     __ _______ __ __
    / / ____ _________ _/ / ___// /_____ ______/ /__
   / / / __ \/ ___/ __ `/ /\__ \/ __/ __ `/ ___/ //_/
  / /___/ /_/ /__/ /_/ /___/ / /_/ /_/ /__/ ,<
/_____/\____/\___/\__,_/_//____/\__/\__,_/\___/_/|_|
💻 LocalStack CLI 0.13.0.11

[20:22:20] starting LocalStack in Docker mode 🐳
[20:22:21] detaching
```

# Configurando AWS-CLI



# Instalando serviços necessários

```
% aws configure
AWS Access Key ID [****************MWWZ]: teste
AWS Secret Access Key [****************cVed]: teste
Default region name [us-east-1]:
Default output format [None]: json
```

## Iam Role da máquina de estado

```
% aws --endpoint http://localhost:4566 iam create-role --role-name role-state-machine --assume-role-policy-document file://iam_policy.json
{
    "Role": {
        "Path": "/",
        "RoleName": "role-state-machine",
        "RoleId": "ql60m6hwcek6do6cam09",
        "Arn": "arn:aws:iam::000000000000:role/role-state-machine",
        "CreateDate": "2022-03-20T00:57:09.011000+00:00",
        "AssumeRolePolicyDocument": {
            "Version": "2012-10-17",
            "Statement": [
                {
                    "Effect": "Allow",
                    "Action": [
                        "lambda:InvokeFunction"
                    ],
                    "Resource": "*"
                }
            ]
        },
        "MaxSessionDuration": 3600
    }
}
```

## Máquina de estado

```
% aws --endpoint http://localhost:4566 stepfunctions create-state-machine --name StepHalloWorld --definition "$(cat state_machine_def.json)" --role-arn arn:aws:iam::000000000000:role/role-state-machine
{
    "stateMachineArn": "arn:aws:states:us-east-1:000000000000:stateMachine:StepHalloWorld",
    "creationDate": "2022-03-19T21:59:57.105000-03:00"
}
```


```
% aws --endpoint http://localhost:4566 stepfunctions start-execution --state-machine-arn arn:aws:states:us-east-1:000000000000:stateMachine:StepHalloWorld --input "{\"first_name\" : \"test\"}"
{
    "executionArn": "arn:aws:states:us-east-1:000000000000:execution:StepHalloWorld:75ef8149-61ac-485b-9476-3443362b33f4",
    "startDate": "2022-03-19T22:04:02.778000-03:00"
}
```

```
aws --endpoint http://localhost:4566 stepfunctions describe-execution --execution-arn arn:aws:states:us-east-1:000000000000:execution:StepHalloWorld:75ef8149-61ac-485b-9476-3443362b33f4
```