resource "kubernetes_namespace" "prometheus" {
  metadata {
    name = "monitoring"
  }
}

resource "helm_release" "prometheus" {
  name       = "main"
  repository = "https://prometheus-community.github.io/helm-charts"
  chart      = "kube-prometheus-stack"
  namespace  = kubernetes_namespace.prometheus.metadata.0.name
  version    = "40.3.1"

  values = [
    "${file("resources/prometheus.yaml")}"
  ]
}