

resource "helm_release" "logging" {
  name       = "main"
  repository = "https://open-telemetry.github.io/opentelemetry-helm-charts"
  chart      = "opentelemetry-operator"
  namespace  = kubernetes_namespace.prometheus.metadata.0.name
  version    = "0.48.0"

  values = [
    "${file("resources/elk.yaml")}"
  ]
}
