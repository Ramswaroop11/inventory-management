data "aws_ami" "amazon_linux" {

  most_recent = true

  owners = ["amazon"]

  filter {
    name   = "name"
    values = ["al2023-ami-*-x86_64"]
  }
}

resource "aws_instance" "inventory" {

  ami = data.aws_ami.amazon_linux.id

  instance_type = "t3.small"

  associate_public_ip_address = true

  iam_instance_profile = aws_iam_instance_profile.inventory_profile.name

  vpc_security_group_ids = [
    aws_security_group.inventory.id
  ]

  root_block_device {
    volume_size = 30
    volume_type = "gp3"
  }

  user_data = <<-EOF
#!/bin/bash

dnf update -y

dnf install -y amazon-ssm-agent docker

growpart /dev/xvda 1 || true
xfs_growfs -d / || true
resize2fs /dev/xvda1 || true

systemctl enable --now amazon-ssm-agent
systemctl enable --now docker

usermod -aG docker ec2-user

mkdir -p /usr/local/lib/docker/cli-plugins

curl -SL https://github.com/docker/compose/releases/latest/download/docker-compose-linux-x86_64 \
-o /usr/local/lib/docker/cli-plugins/docker-compose

chmod +x /usr/local/lib/docker/cli-plugins/docker-compose

docker --version

docker compose version
EOF

  depends_on = [
    aws_iam_role_policy_attachment.ssm,
    aws_iam_role_policy_attachment.ecr
  ]

  tags = {
    Name = "inventory-management"
  }
}
