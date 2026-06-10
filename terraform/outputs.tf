output "ecr_url" {
  value = aws_ecr_repository.inventory.repository_url
}

output "instance_id" {
  value = aws_instance.inventory.id
}

output "public_ip" {
  value = aws_instance.inventory.public_ip
}