export interface AlertsData {
  cna: string,
  cxp: string,
  testName: string,
  date: Date
}

export interface TestAlertReport {
  projectName: string,
  cxpNumber: string,
  buildNumber: number,
  buildDateTimestamp: number,
  buildReleaseType: string,
  cnaName: string
}
