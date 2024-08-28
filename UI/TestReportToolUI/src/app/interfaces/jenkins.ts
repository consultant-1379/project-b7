export interface JenkinsTestReport {
  projectName: string,
  cxpNumber: string,
  testsPassed: number,
  testsFailed: number,
  testsSkipped: number,
  successRate: number,
  buildNumber: number,
  buildDateTimestamp: string,
  buildReleaseType: string,
  failureRate: number
}
