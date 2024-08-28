import { GraphPoint } from "../interfaces/graph";

export class GraphMap {
  private items: { [key: string]: GraphPoint };

  constructor() {
      this.items = {};
  }

  add(key: string, value: GraphPoint): void {
      this.items[key] = value;
  }

  has(key: string): boolean {
      return key in this.items;
  }
  update(key: string, value: GraphPoint): void {
    if(this.has(key)) {
      this.items[key] = {
        totalTests: Number(this.items[key].totalTests + value.totalTests),
        testsFailed: Number(this.items[key].testsFailed + value.testsFailed),
        testsPassed: Number(this.items[key].testsPassed + value.testsPassed),
        testsSkipped: Number(this.items[key].testsSkipped + value.testsSkipped)
      }
    }
    else {
      this.add(key, value);
    }

  }
  get(key: string): GraphPoint {
      return this.items[key];
  }
}
