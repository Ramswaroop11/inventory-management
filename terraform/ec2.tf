data "aws_ami" "amazon_linux" {

  most_recent = true

  owners = ["amazon"]

  filter {
    name   = "name"
    values = ["al2023-ami-*-x86_64"]
  }
}

resource "aws_instance" "inventory" {

  ami           = data.aws_ami.amazon_linux.id

  instance_type = "t3.micro"

  associate_public_ip_address = true

  iam_instance_profile = aws_iam_instance_profile.inventory_profile.name

  vpc_security_group_ids = [
    aws_security_group.inventory.id
  ]

  user_data = <<-EOF
#!/bin/bash

dnf update -y

dnf install docker -y

systemctl enable docker

systemctl start docker

usermod -aG docker ec2-user

curl -SL https://github.com/docker/compose/releases/latest/download/docker-compose-linux-x86_64 \
-o /usr/local/bin/docker-compose

chmod +x /usr/local/bin/docker-compose

docker --version

docker-compose --version
EOF

  tags = {
    Name = "inventory-management"
  }
}