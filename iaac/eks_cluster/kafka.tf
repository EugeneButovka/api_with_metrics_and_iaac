resource "kubernetes_namespace" "kafka" {
  metadata {
    name = "logging"
  }
}

resource "helm_release" "kafka" {
  name       = "main"
  repository = "https://kafka-community.github.io/helm-charts"
  chart      = "kube-kafka-stack"
  namespace  = kubernetes_namespace.kafka.metadata.0.name
  version    = "3.6.1"

  values = [
    "${file("resources/kafka.yaml")}"
  ]
}