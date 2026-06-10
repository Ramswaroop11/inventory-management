resource "aws_ecr_repository" "inventory" {

  name = "inventory-management"

  image_tag_mutability = "MUTABLE"

  image_scanning_configuration {
    scan_on_push = true
  }
}